package fr.gol.multi;

/**
 *
 */
public class Player {

    private final String nickname;

    public Player(String nickame) {
        this.nickname=nickame;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return !(nickname != null ? !nickname.equals(player.nickname) : player.nickname != null);

    }

    @Override
    public int hashCode() {
        return nickname != null ? nickname.hashCode() : 0;
    }

    public String getNickname() {
        return nickname;
    }
}
