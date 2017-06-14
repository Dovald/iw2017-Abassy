echo "Se va a proceder a entrar en MySQL server para crear la base de datos de Abassy"
echo "Introduzca usuario de MySQL: "
read  user
mysql -u $user -p -e "create database abassy_db" 
cd ./main/
sudo mvn tomcat:run
echo "Base de datos instalada"
