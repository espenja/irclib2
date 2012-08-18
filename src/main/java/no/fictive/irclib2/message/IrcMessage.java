package no.fictive.irclib2.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing all relevant fields for a message from an IRC server
 */
public class IrcMessage {

	private String message = "";
	private String prefix = "";
	private String command = "";
	private List<String> parameters = new ArrayList<String>();

	private int index = 0;

	//Roughly 2.5-3.0 seconds per 1 000 000 messages
	public IrcMessage(String message) {
		this.message = message;
		parseMessage(message);
	}

	/**
	 * Parses the message line into sections.
	 * @see <a href="http://irchelp.org/irchelp/rfc/rfc2812.txt"> RFC2812 </a>
	 */
	private void parseMessage(String message) {

		/* 	<message> 	::=	[':' <prefix> <SPACE> ] <command> <params> <crlf>
		 *	<prefix> 	::=	<servername> | <nick> [ '!' <user> ] [ '@' <host> ]
		 *	<command> 	::=	<letter> { <letter> } | <number> <number> <number>
		 *	<SPACE> 	::=	' ' { ' ' }
		 *	<params>	::=	<SPACE> [ ':' <trailing> | <middle> <params> ]
		 *	<middle> 	::=	<Any *non-empty* sequence of octets not including SPACE or NUL or CR or LF, the first of which may not be ':'>
		 *	<trailing> 	::=	<Any, possibly *empty*, sequence of octets not including NUL or CR or LF>
		 *	<crlf> 		::=	CR LF
		 */

		if(message.startsWith(":")) {
			findPrefix();
			findNextNonWhite();
		}
		findCommand();
		findNextNonWhite();
		findParameters();
	}

	/**
	 * Finds the next non-white character in the message.
	 */
	private void findNextNonWhite() {
		for(int i = index; i < message.length(); i++) {
			if(message.charAt(i) != ' ') {
				index = i;
				break;
			}
		}
	}

	/**
	 * Finds the prefix in the message.
	 */
	private void findPrefix() {
		//	<prefix> ::= <servername> | <nick> [ '!' <user> ] [ '@' <host> ]

		prefix = message.substring(1, message.indexOf(' '));
		index += prefix.length() + 2;
	}

	/**
	 * Finds the command in the message.
	 */
	private void findCommand() {

		//	<command> ::= <letter> { <letter> } | <number> <number> <number>

		int nextspace = message.substring(index).indexOf(' ');
		command = message.substring(index).substring(0, nextspace).trim();
		index += command.length();
	}

	/**
	 * Finds the parameters in the message.
	 */
	private void findParameters() {
		String parameters = message.substring(index);
		parseParameters(parameters);
	}


	/**
	 * Parses the parameters in the message.
	 * @param parameters A String containing the parameters of the message.
	 */
	private void parseParameters(String parameters) {

		//	<params> 		::= <SPACE> [ ':' <trailing> | <middle> <params> ]
		//	<middle>		::= nospcrlfcl [ ':' <middle> ]
		//	<trailing>		::=	[ ':' | ' ' | nospcrlfcl <trailing>]
		//	<nospcrlfcl>	::= any character value except NUL (0), CR (13), LF (10), " " (34), ":" (58)

		String[] parametersArray = parameters.split("\\s");

		for(int i = 0; i < parametersArray.length; i++) {
			if(parametersArray[i].charAt(0) == ':') {

				/*
				 * Concatinate the rest of the array into one string.
				 * If a parameter starts with ':', the rest of the line is one parameter.
				 */
				String parametersConcatinated = parametersArray[i].substring(1);
				for(int j = i+1; j < parametersArray.length; j++) {
					parametersConcatinated += " " + parametersArray[j];
				}
				this.parameters.add(parametersConcatinated);
				return;
			}
			else {
				this.parameters.add(parametersArray[i]);
			}
		}
	}

	/**
	 * Find out if the message is a numeric message or not.
	 * @return <code>true</code> if the message is a numeric message; <code>false</code> if not.
	 *
	 */
	public boolean isNumeric() {
		try {
			Integer.parseInt(command);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the raw message.
	 * @return The raw message from the IRC server.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the parameters for this message
	 * @return The parameters for this message.
	 */
	public List<String> getParameters() {
		return new ArrayList<String>(this.parameters);
	}

	/**
	 * Gets the command for this message.
	 * @return The command for this message.
	 *
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Gets the nick associated with this message.
	 * @return The nick associated with this message. If no nick can be found, returns an empty string.
	 */
	public String getNickname() {
		if(prefix.contains("!"))
			return prefix.substring(0, prefix.indexOf('!'));
		else
			if(prefix.contains("@"))
				return prefix.substring(0, prefix.indexOf("@"));
		return prefix;
	}

	/**
	 * Gets the ident associated with this message.
	 * @return The ident associated with this message. If no ident can be found, returns an empty string.
	 */
	public String getIdent() {
		if(prefix.contains("!"))
			return prefix.substring(prefix.indexOf("!") + 1, prefix.indexOf("@"));
		return "";
	}

	/**
	 * Gets the hostname associated with this message.
	 * @return The hostname associated with this message. If no hostname can be found, returns an empty string.
	 */
	public String getHost() {
		if(prefix.contains("@"))
			return prefix.substring(prefix.indexOf("@") + 1);
		return "";
	}

	/**
	 * Gets the server name associated with this message.
	 * Use with caution; does no checks as to if this actually is a host name.
	 * Should perhaps be named "getPrefix".
	 * @return The server associated with this message.
	 */
	public String getServerName() {
		return prefix;
	}
}