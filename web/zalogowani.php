<?php include("config.php");
$login = $_SESSION['login'];
$haslo = $_SESSION['haslo'];
    if ((empty($login)) AND (empty($haslo))) {
echo '<br>Nie by�e� zalogowany albo zosta�e� wylogowany<br><a href="index.php">Strona G��wna</a><br>';
exit;
}
$user = mysql_fetch_array(mysql_query("SELECT * FROM bol_users WHERE `login`='$login' AND `password`='$haslo' LIMIT 1"));
    if (empty($user["id"]) OR !isset($user["id"])) {
echo '<br>Nieprawid�owe logowanie.<br>';
exit;
}
// tresc dla zalogowanego uzytkownika
echo 'Witaj '.$user["login"].' zosta�e�/a� pomy�lnie zalogowany/a, tutaj umie�� ukryta strone tylko dla zalogowanych';
echo '<br><a href="wyloguj.php">Wyloguj mnie</a>';
?>