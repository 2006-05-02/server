package org.apollo.game.model.inter.dialogue;

import org.apollo.game.model.entity.Player;

/**
 * An adapter for the {@link DialogueListener}.
 *
 * @author Chris Fletcher
 */
public abstract class DialogueAdapter implements DialogueListener {

	@Override
	public boolean buttonClicked(int button) {
		return false;
	}

	@Override
	public void continued(Player player) {

	}

	@Override
	public void interfaceClosed() {

	}

}