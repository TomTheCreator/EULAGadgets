package me.tomthedeveloper;

import me.tomthedeveloper.Attacks.Attack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Tom on 27/07/2014.
 */
public class User {

    private ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private Scoreboard scoreboard;
    public static EULAGadgets plugin;
    private static int COOLDOWNCOUNTER = 0;
    private UUID uuid;
    private UUID lasthitted;
    private int power;
    private int exp;
    private boolean fakedead = false;
    private boolean spectator = false;
    private boolean doublejump = false;
    private boolean hasdoublejumped = false;
    private Attack attack;

    private HashMap<String, Integer> ints = new HashMap<String, Integer>();
    private HashMap<String, Integer> cooldowns = new HashMap<String, Integer>();





    public User(UUID uuid){
        scoreboard = scoreboardManager.getNewScoreboard();
        this.uuid = uuid;
    }

    public void setScoreboard(Scoreboard scoreboard){
        Bukkit.getPlayer(uuid).setScoreboard(scoreboard);
    }



    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getPower(){
        return power;
    }

    public void setPower(int power){
        this.power = power;
    }

    public void addPower(){
        setPower(getPower() + 1);
    }

    public void addPower(int i){
        setPower(getPower() + i);
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void addExp(int exp){
        setExp(getExp() + exp);
    }

    public void addExp(){
        setExp(getExp() + 1);
    }

    public void setFakeDead(boolean b){
        fakedead = b;
    }

    public boolean isFakeDead(){
        return fakedead;
    }

    public Player toPlayer(){

        return Bukkit.getServer().getPlayer(uuid);
    }

    public void removePower(int i){
        setPower(getPower() -i);
    }

    public void setSpectator(boolean b){
        spectator = b;
    }

    public boolean isSpectator(){
        return spectator;
    }

    public void allowDoubleJump(){
        doublejump = true;
    }



    public boolean hasDoubleJumped(){
       return hasdoublejumped;
    }

    public void reNewDoubleJump(){
        hasdoublejumped = false;
    }

    public void doubleJumped(){
        hasdoublejumped = true;
    }

    public int getInt(String s){
        if(!ints.containsKey(s))
            ints.put(s, 0);
        return ints.get(s);




    }

    public void removeScoreboard(){
        this.toPlayer().setScoreboard(scoreboardManager.getNewScoreboard());
    }

    public void setInt(String s, int i){
        ints.put(s, i);

    }



    public void addInt(String s, int i){
        ints.put(s, getInt(s)+i);
    }

    private enum Rank{
        VIP, MVP, ELITE;
    }

    public void setAllowDoubleJump(boolean b){
        doublejump = b;
    }

    public static void handleCooldowns(){
       COOLDOWNCOUNTER++;
    }

    public void setCooldown(String s, int seconds){
       cooldowns.put(s, seconds*20 + COOLDOWNCOUNTER);
    }

    public int getCooldown(String s){
        if(!cooldowns.containsKey(s))
            return 0;
        if(cooldowns.get(s) <=COOLDOWNCOUNTER)
            return 0;
        return (cooldowns.get(s)-COOLDOWNCOUNTER)/20;
    }

    public void removeInt(String string, int i){
        ints.put(string, ints.get(string) -i);
    }

    public Player getLastHitted(){
        if(lasthitted == null)
            return null;
        return Bukkit.getPlayer(lasthitted);
    }

    public void setLastHitted(Player player){
        lasthitted = player.getUniqueId();
    }

    public void setAttack(Attack attack){

        this.attack = attack;
    }

    public Attack getAttack(){
        return this.attack;
    }

    public void unRegisterAttacks(){
        if(attack != null) {
            plugin.getAttackManager().unregisterAttack(attack);


        }
        attack = null;
    }







}
