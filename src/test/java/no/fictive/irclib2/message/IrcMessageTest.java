package no.fictive.irclib2.message;

import no.fictive.irclib2.message.IrcMessage;

import org.junit.Test;
import static org.junit.Assert.*;

public class IrcMessageTest {

	@Test
	public void joinMessageParametersEndResult() {
		IrcMessage joinMessage = new IrcMessage(StaticMessages.join);
		//Parameter 0 for a join message should be the joined channels name.
		assertEquals("#online.irclib", joinMessage.getParameters().get(0));
	}

	@Test
	public void joinMessageCommandShouldEqualJOIN() {
		IrcMessage joinMessage = new IrcMessage(StaticMessages.join);
		//Command for a join message should be "JOIN"
		assertEquals("JOIN", joinMessage.getCommand());
	}

	@Test
	public void nickMessageParametersEndResult() {
		IrcMessage nickMessage = new IrcMessage(StaticMessages.nick);
		//Parameter 0 for a nick message should be the new nick
		assertEquals("irclib2", nickMessage.getParameters().get(0));
	}

	@Test
	public void nickMessageCommandShouldEqualNICK() {
		IrcMessage joinMessage = new IrcMessage(StaticMessages.nick);
		//Command for a nick message should be "NICK"
		assertEquals("NICK", joinMessage.getCommand());
	}

	@Test
	public void privmsgMessageParametersEndResult() {
		IrcMessage privmsgMessage = new IrcMessage(StaticMessages.privmsg);
		//Parameter 0 for a privmsg message should be the channel or user receiving the message
		assertEquals("#online.irclib", privmsgMessage.getParameters().get(0));
		//Parameter 1 for a privmsg message should be the message itself
		assertEquals("testing privmsg", privmsgMessage.getParameters().get(1));
	}

	@Test
	public void ptivmsgMessageCommandShouldEqualPRIVMSG() {
		IrcMessage privmsg = new IrcMessage(StaticMessages.privmsg);
		assertEquals("PRIVMSG", privmsg.getCommand());
	}

	@Test
	public void pingMessageParametersEndResult() {
		IrcMessage pingMessage = new IrcMessage(StaticMessages.ping);
		assertEquals("chat.freenode.net", pingMessage.getParameters().get(0));
	}

	@Test
	public void pingMessageCommandShouldEqualPING() {
		IrcMessage pingMessage = new IrcMessage(StaticMessages.ping);
		assertEquals("PING", pingMessage.getCommand());
	}

	@Test
	public void modeMessageParametersEndResult() {
		IrcMessage modeMessage = new IrcMessage(StaticMessages.mode);
		assertEquals("#online.irclib", modeMessage.getParameters().get(0));
		assertEquals("+ovo", modeMessage.getParameters().get(1));
		assertEquals("nickname1", modeMessage.getParameters().get(2));
		assertEquals("nickname2", modeMessage.getParameters().get(3));
		assertEquals("nickname3", modeMessage.getParameters().get(4));
	}

	@Test
	public void modeMessageCommandShouldEqualMODE() {
		IrcMessage modeMessage = new IrcMessage(StaticMessages.mode);
		assertEquals("MODE", modeMessage.getCommand());
	}

	@Test
	public void shouldReturnNickname() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.join);
		assertEquals("irclib", aMessage.getNickname());

		aMessage = new IrcMessage(StaticMessages.messageWithoutIdent);
		assertEquals("irclib", aMessage.getNickname());

		aMessage = new IrcMessage(StaticMessages.messageWithoutIdentAndHost);
		assertEquals("irclib", aMessage.getNickname());
	}

	@Test
	public void shouldReturnEmptyNickname() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.ping);
		assertEquals("", aMessage.getNickname());
	}

	@Test
	public void shouldReturnIdent() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.join);
		assertEquals("user", aMessage.getIdent());
	}

	@Test
	public void shouldReturnEmptyIdent() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.messageWithoutIdent);
		assertEquals("", aMessage.getIdent());
	}

	@Test
	public void shouldReturnHost() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.join);
		assertEquals("host.somewhere.no", aMessage.getHost());
	}

	@Test
	public void shouldReturnEmptyHost() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.messageWithoutIdentAndHost);
		assertEquals("", aMessage.getHost());
	}

	@Test
	public void shouldReturnServerName() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.pong);
		assertEquals("niven.freenode.net", aMessage.getServerName());
	}

	@Test
	public void shouldBeANumericMessage() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.endofMOTD);
		assertTrue(aMessage.isNumeric());
	}

	@Test
	public void shouldNotBeANumericMessage() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.join);
		assertFalse(aMessage.isNumeric());
	}

	@Test
	public void shouldReturnRawMessage() {
		IrcMessage aMessage = new IrcMessage(StaticMessages.endofMOTD);
		assertEquals(StaticMessages.endofMOTD, aMessage.getMessage());
	}
}
