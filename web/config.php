<?php session_start();
	mysql_connect("grm-dev.pl","BOL","nPGU7t3v3TsJByNH") or die(mysql_error()."Nie mozna polaczyc sie z baza danych. Prosze chwile odczekac i sprobowac ponownie.");
	mysql_select_db("BattleOfLegends") or die(mysql_error()."Nie mozna wybrac bazy danych.");
?>