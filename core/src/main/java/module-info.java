/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides advanced native language support.<br>
 * <h2>Native Language Support (NLS)</h2><br>
 * Applications that should be used by people all over the world need <em>native language support</em> (NLS). The
 * developers task is the <em>internationalization</em> (i18n) where the application has to be written in a way that the
 * code is (mostly) independent from locale-specific informations. This is a challenging task that affects many aspects
 * like GUI-dialogs as well as all text-messages displayed to the end-user. The NLS provided here only addresses the
 * internationalization of text-messages in a way that allows <em>localization</em> (l10n) to the users locale. <br>
 * <h3>The Problem</h3>
 * <p>
 * <img src="{@docRoot}/doc-files/no-msg-dialog.png"/>
 * </p>
 * <p>
 * Java already comes with great i18n support. But IMHO there are some tiny peaces missing to complete the great puzzle
 * of NLS:<br>
 * There is almost no support if an application needs NLS that is handling multiple users with different locales
 * concurrently (e.g. a web-application). <br>
 * You will typically store your messages in a {@link java.util.ResourceBundle}. Now if you store the technical key of
 * the bundle in a message or exception the receiver needs the proper {@link java.util.ResourceBundle} to decode it or
 * he ends up with a cryptic message he can NOT understand (e.g. as illustrated by the screenshot). <br>
 * On the other hand you need to know the locale of the receiver to do the l10n when creating the message or exception
 * with the proper text. This may lead to sick design such as static hacks. Also if you have to translate the text at
 * the creation of the message every receiver has to live with this language. Especially for logging this is a big
 * problem. An operator will be lost in space if he gets such logfiles:
 *
 * <pre>
 * [2000-01-31 23:59:00,000][ERROR][n.s.m.u.n.a.MasterService] The given value (256) has to be in the range from 0 to 100.
 * [2000-01-31 23:59:01,000][WARN ][n.s.m.u.n.a.MasterService] Der Benutzername oder das Passwort sind ungültig.
 * [2000-01-31 23:59:02,000][ERROR][n.s.m.u.n.a.MasterService] 文件不存在。
 * [2000-01-31 23:59:03,000][FATAL][n.s.m.u.n.a.MasterService] ข้อผิดพลาดที่ไม่คาดคิดได้เกิดขึ้น
 * </pre>
 * </p>
 * <h3>The Solution</h3> The solution is quite simple:<br>
 * We simply bundle the message in default language together with the separated dynamic arguments in one container
 * object that is called {@link io.github.mmm.nls.NlsMessage}. For exceptions there is additional support via
 * {@link io.github.mmm.base.exception.ApplicationException}. Here is an example to clarify the idea of
 * {@link io.github.mmm.nls.NlsMessage}: The i18n message is "Hi {name}! How are you?" and the dynamic argument is the
 * users name e.g. "Lilli". Now if we store this information together we have all we need. To get the localized message
 * we simply translate the i18n message to the proper language and then fill in the arguments. If we can NOT translate
 * we always have the message in default language which is "Hi Lilli! How are you?". <br>
 * But how do we translate the i18n message to other languages? The answer is quite easy:
 * <h4>NlsBundle</h4> The recommended approach is to create a final class derived from
 * {@link io.github.mmm.nls.NlsBundle}. For each message you define a method that takes the arguments to fill in and
 * returns an {@link io.github.mmm.nls.NlsMessage}:
 *
 * <pre>
 * package foo.bar;
 *
 * public final class NlsBundleExample extends {@link io.github.mmm.nls.NlsBundle} {
 *
 *   public static final NlsBundleExample INSTANCE = new NlsBundleExample();
 *
 *   {@link io.github.mmm.nls.NlsMessage} messageSayHi(String name) {
 *     return create("messageSayHi", "Hi {name}! How are you?", {@link io.github.mmm.nls.argument.NlsArguments#ofName(Object) NlsArguments.ofName}(name));
 *   }
 *
 *   {@link io.github.mmm.nls.NlsMessage} errorLoginInUse(String login) {
 *     return create("errorLoginInUse", "Sorry. The login '{name}' is already in use. Please choose a different login.", {@link io.github.mmm.nls.argument.NlsArguments#ofName(Object) NlsArguments.ofName}(login));
 *   }
 * }
 * </pre>
 *
 * From your code you now can do this:
 *
 * <pre>
 * String userName = "Lilli";
 * {@link io.github.mmm.nls.NlsMessage} msg = NlsBundleExample.INSTANCE.messageSayHi(userName);
 * String text = msg.{@link io.github.mmm.nls.NlsMessage#getMessage() getMessage}());
 * String textDefault = msg.{@link io.github.mmm.nls.NlsMessage#getLocalizedMessage() getLocalizedMessage}());
 * String textDe = msg.{@link io.github.mmm.nls.NlsMessage#getLocalizedMessage(java.util.Locale) getLocalizedMessage}({@link java.util.Locale#GERMAN}));
 * </pre>
 *
 * For the error message create an exception like this:
 *
 * <pre>
 * public class LoginAlreadyInUseException extends {@link io.github.mmm.base.exception.ApplicationException} {
 *
 *   public LoginAlreadyInUseException(String login) {
 *     this(null, login);
 *   }
 *
 *   public LoginAlreadyInUseException(Throwable cause, String login) {
 *     super(NlsBundleExample.INSTANCE.errorLoginInUse(login), cause);
 *   }
 * }
 * </pre>
 *
 * For further details see {@link io.github.mmm.nls.NlsBundle}. <br>
 * <br>
 * For localization you can create property files with the translations of your NLS-bundle. E.g.
 * {@code foo/bar/NlsBundleExample_de.properties} with this content:
 *
 * <pre>
 * messageSayHi = Hallo {name}! Wie geht es Dir?
 * errorLoginInUse = Es tut uns leid. Das Login "{login}" ist bereits vergeben. Bitte wählen Sie ein anderes Login.
 * </pre>
 *
 * Unlike the Java defaults, you will use named parameters instead of indexes what makes it much easier for localizers.
 * There are even more advanced features such as recursive translation of arguments and choice format type. See
 * {@link io.github.mmm.nls.NlsMessage} for further details.
 *
 * In order to support you with creating and maintaining the localized properties, this solution also comes with the
 * {@code io.github.mmm.nls.sync.NlsSynchronizer}. <br>
 * <h3>Conclusion</h3> As we have seen the NLS provided here makes it very easy for developers to write and maintain
 * internationalized code. While messages are created throughout the code they only need to be localized for the
 * end-user in the client and at service-endpoints. Only at these places you need to figure out the users locale (e.g.
 * using {@code org.springframework.context.i18n.LocaleContextHolder}).
 * <ul>
 * <li>The {@link io.github.mmm.nls.NlsMessage} allows to store an internationalized message together with actual
 * arguments to fill in.</li>
 * <li>The arguments can be arbitrary objects including {@link io.github.mmm.base.i18n.LocalizableObject}s that will be
 * localized recursively.</li>
 * <li>There are powerful ways to format these arguments including variable expressions for optional arguments or plural
 * forms. See {@link io.github.mmm.nls.NlsMessage} for advanced examples.</li>
 * <li>Instead of numbered arguments we support named arguments what makes maintenance of the messages a lot easier.
 * Your localizers will love you for choosing this solution.</li>
 * <li>Resource bundle properties are read in UTF-8 encoding making it easier for localizers as they do not have to
 * escape characters to unicode number sequences.</li>
 * <li>The localization (translation to native language) is easily performed by
 * {@link io.github.mmm.nls.NlsMessage#getLocalizedMessage(java.util.Locale)}.</li>
 * <li>For exceptions there is additional support via {@link io.github.mmm.base.exception.ApplicationException}.</li>
 * </ul>
 */
module io.github.mmm.nls {

  requires transitive io.github.mmm.scanner;

  requires org.slf4j;

  exports io.github.mmm.nls;

  exports io.github.mmm.nls.argument;

  exports io.github.mmm.nls.descriptor;

  exports io.github.mmm.nls.formatter;

  exports io.github.mmm.nls.template;

  exports io.github.mmm.nls.variable;

}
