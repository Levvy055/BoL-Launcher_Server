<!DOCTYPE HTML>

<html lang="pl">
    <head>
        <meta charset="utf-8"/>
    </head>
    <body>
        <?php
			$login = $_POST['login'];
			$pass = $_POST['pass'];
            
			if($login == "a" && $pass == "a") {
echo<<<END

	<title>BoL $login</title>
	
	Zalogowano!!
	<br>
	<br>
	<form action="index.php">
		<input type="submit" value="Wyloguj" />
	</form>

END;
			} else {
				echo "Zly login lub haslo!!";
			}
        ?>
        <br /><br />
    </body>
</html>