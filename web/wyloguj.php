<html>
<body>
<?php session_start();
echo "Wylogowano<br><br>";
session_destroy();
?>
<a href="index.php">Powrot do strony glownej</a>
</body>
</html>