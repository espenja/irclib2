package no.fictive.irclib2.message;

import no.fictive.irclib2.message.IrcMessage;

import org.junit.Test;
import static org.junit.Assert.*;

public class IrcMessageTest {

	@Test
	public void parametersEndResultForJoinMessage() {
		IrcMessage joinMessage = new IrcMessage(StaticMessages.join);

		String expectedValue = "#online.irclib";
		String actualValue = joinMessage.getParameters().get(0);
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void commandForJoinResultShouldBeJOIN() {
		IrcMessage joinMessage = new IrcMessage(StaticMessages.join);
		assertEquals("JOIN", joinMessage.getCommand());
	}

	@Test
	public void parametersEndResultForNickMessage() {
		IrcMessage nickMessage = new IrcMessage(StaticMessages.nick);

		String expectedValue = "irclib2";
		String actualValue = nickMessage.getParameters().get(0);
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void commandForNickResultShouldBeNICK() {
		IrcMessage joinMessage = new IrcMessage(StaticMessages.nick);
		assertEquals("NICK", joinMessage.getCommand());
	}

	@Test
	public void parametersEndResultForPrivmsgMessage() {
		IrcMessage privmsgMessage = new IrcMessage(StaticMessages.privmsg);

		String expectedValue = "testing privmsg";
		String actualValue = privmsgMessage.getParameters().get(1);
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void commandForPrivMsgShouldBePRIVMSG() {
		IrcMessage privmsg = new IrcMessage(StaticMessages.privmsg);
		assertEquals("PRIVMSG", privmsg.getCommand());
	}

	@Test
	public void parametersEndResultForPing() {
		IrcMessage pingMessage = new IrcMessage(StaticMessages.ping);

		String expectedValue = "chat.freenode.net";
		String actualValue = pingMessage.getParameters().get(0);
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void commandForPingShouldBePING() {
		IrcMessage pingMessage = new IrcMessage(StaticMessages.ping);
		assertEquals("PING", pingMessage.getCommand());
	}
}
