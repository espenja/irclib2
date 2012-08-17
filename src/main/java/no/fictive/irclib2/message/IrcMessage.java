package no.fictive.irclib2.message;

import java.util.ArrayList;
import java.util.List;

public class IrcMessage {

	private String message;
	private String prefix;
	private String command;
	private List<String> parameters = new ArrayList<String>();

	private int index = 0;

	public IrcMessage(String message) {
		this.message = message;
		parseMessage(message);
	}

	/**
	 * Parses the raw line into sections.
	 * @see <a href="http://irchelp.org/irchelp/rfc/rfc2812.txt"> RFC2812 </a>
	 *
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
	 * Finds the next non-white character in the raw-line.
	 *
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
	 * Finds the prefix in the raw-line.
	 *
	 */
	private void findPrefix() {
		//	<prefix> ::= <servername> | <nick> [ '!' <user> ] [ '@' <host> ]

		prefix = message.substring(1, message.indexOf(' '));
		index += prefix.length() + 2;
	}


	/**
	 * Finds the command in the raw-line.
	 *
	 */
	private void findCommand() {

		//	<command> ::= <letter> { <letter> } | <number> <number> <number>

		int nextspace = message.substring(index).indexOf(' ');
		command = message.substring(index).substring(0, nextspace).trim();
		index += command.length();
	}


	/**
	 * Finds the parameters in the raw-line.
	 *
	 */
	private void findParameters() {
		String parameters = message.substring(index);
		parseParameters(parameters);
	}


	/**
	 * Parses the parameters in the raw-line.
	 * @param parameters A String containing the parameters of the raw-line.
	 *
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

	public List<String> getParameters() {
		return new ArrayList<String>(this.parameters);
	}

	public String getCommand() {
		return command;
	}
}