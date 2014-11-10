<!DOCTYPE HTML>

<html lang="pl">
    <head>
        <meta charset="utf-8"/>
        <title>Battle of Legends</title>
    </head>
	
    <body>
		<h1>Zaloguj:</h1>
		
		<form action="login.php" method="post">
			Login:
			<input type="text" name="login" />
			<br /><br />
			
			Hasło:
			<input type="password" name="pass" />
			<br /><br />
			
			<input type="submit" value="Zaloguj" />
		</form>
		<br />
		
		<form action="register.php" method="post">
			<input type="submit" value="Rejestracja" />
		</form>
	
        <?php
            
        ?>
        <br /><br />
    </body>
</html>