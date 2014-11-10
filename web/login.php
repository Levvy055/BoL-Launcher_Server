<?php 
include("config.php");
include("hashphp.php");

$login = $_POST['login'];
$haslo = $_POST['haslo'];
$haslo = addslashes($haslo);
$login = addslashes($login);
$login = htmlspecialchars($login);

if(isset($_GET["login"])) {
	if ($_GET["login"] != "") { //jezeli ktos przez adres probuje kombinowac
	exit;
	}
}
if(isset($_GET["haslo"])) {
	if ($_GET["haslo"] != "") { //jezeli ktos przez adres probuje kombinowac
	exit;
	}
}

$haslo = create_hash($haslo); //szyfrowanie hasla
    if (!$login OR empty($login)) {
include("head2.php");
echo '<p class="alert">Wype³nij pole z loginem!</p>';
include("foot.php");
exit;
}
    if (!$haslo OR empty($haslo)) {
include("head2.php");
echo '<p class="alert">Wype³nij pole z has³em!</p>';
include("foot.php");
exit;
}
$istlogin = mysql_fetch_array(mysql_query("SELECT COUNT(*) FROM `bol_users` WHERE `login` = '$login' AND `password` = '$haslo'")); // sprawdzenie czy istnieje uzytkownik o takim loginu i hasle
    if ($istlogin[0] == 0) {
echo 'Logowanie nieudane. SprawdŸ pisowniê loginu oraz has³a.';
    } else {

$_SESSION['login'] = $login;
$_SESSION['haslo'] = $haslo;

header("Location: zalogowani.php");
}
?>