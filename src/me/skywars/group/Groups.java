package me.skywars.group;

import org.bukkit.entity.Player;

public enum Groups {
	
	MEMBRO("membro", "§7", "skywars.tag.membro", "§7"),
	SOLDADO("Soldado", "§a[SOLDADO] ", "skywars.tag.soldado", "§a"),
	GUERREIRO("Guerreiro", "§9[GUERREIRO] ", "skywars.tag.guerreiro", "§9"),
	ELITE("Elite", "§5[ELITE] ", "skywars.tag.elite", "§5"),
	MINIYT("Miniyt", "§b[MINIYT] ", "skywars.tag.miniyt", "§b"),
	YT("Yt", "§c[YT] ", "skywars.tag.yt", "§c"),
	YTPLUS("Ytplus", "§3[YT+] ", "skywars.tag.ytplus", "§3"),
	AJUDANTE("Ajudante", "§e[AJD] ", "skywars.tag.ajudante", "§e"),
	MODERADOR("Moderador", "§e[MOD] ", "skywars.tag.moderador", "§2"),
	ADMIN("Admin", "§c[ADM] ", "skywars.tag.admin", "§c"),
	GERENTE("Gerente", "§4[GER] ", "skywars.tag.gerente", "§4"),
	COORDENADOR("Coordenador", "§e[COORD] ", "skywars.tag.coord", "§e"),
	DEV("Desenvolvedor", "§5[DEV] ", "skywars.tag.dev", "§5"),
	DONO("Dono", "§4[DONO] ", "skywars.tag.dono", "§4");

	private String teamName, tag, permission, cor;

	Groups(String teamName, String tag, String permission, String cor) {
		this.teamName = teamName;
		this.tag = tag;
		this.permission = permission;
		this.cor = cor;
	}
	
	public boolean has(Player player) {
		return permission.isEmpty() || player.hasPermission(permission);
	}

	public static Groups getGroup(Player player) {
		for (Groups group : values()) {
			if (group.has(player)) {
				return group;
			}
		}
		return MEMBRO;
	}

	public String getTag() {
		return tag;
	}

	public String getTeamName() {
		return teamName;
	}
	
	public String getCor() {
		return cor;
	}

}
