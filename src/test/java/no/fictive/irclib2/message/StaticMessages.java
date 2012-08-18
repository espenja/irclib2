package no.fictive.irclib2.message;


public class StaticMessages {
	public static final String join = ":irclib!user@host.somewhere.no JOIN #online.irclib";
	public static final String mode = ":irclib!user@host.somewhere.no MODE #online.irclib +ovo nickname1 nickname2 nickname3";
	public static final String nick = ":irclib!user@host.somewhere.no NICK :irclib2";
	public static final String ping = "PING chat.freenode.net";
	public static final String pong = ":niven.freenode.net PONG niven.freenode.net :chat.freenode.net";
	public static final String privmsg = ":irclib!user@host.somewhere.no PRIVMSG #online.irclib :testing privmsg";

	public static final String endofMOTD = ":irc.freenode.org 376 nick :End of /MOTD command.";

	public static final String messageWithoutIdentAndHost = ":irclib COMMAND #online.irclib";
	public static final String messageWithoutIdent = ":irclib@host.somewhere.no COMMAND #online.irclib";

}
