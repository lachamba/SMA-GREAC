<html>
<?php
require('../config.php');
require_once('lib.php');
require_once($CFG->dirroot.'/user/edit_form.php');
require_once($CFG->dirroot.'/user/editlib.php');
require_once($CFG->dirroot.'/user/profile/lib.php');
require_once($CFG->dirroot.'/user/lib.php');

$PAGE->set_pagetype('site-index');
$PAGE->set_docs_path('');
$PAGE->set_pagelayout('frontpage');
$editing= $PAGE->user_is_editing();
$PAGE->set_title($SITE->fullname);
$PAGE->set_heading($SITE->fullname);
$courserenderer=$PAGE->get_renderer('core','course');
echo $OUTPUT->header();

$siteinformatoptions=course_get_format($SITE)->get_format_options();
	$modinfo=get_fast_modinfo($SITE);
	$modnames=get_module_types_names();
	$modnamesplural=get_module_types_names(true);
	//$modnamesused=$modinfo->get_module_names();
	$mods=$modinfo->get_cms();


if(count($_POST)!=0)
{
	$array=$_POST["resp"];
	$cadaux=implode($array);

	$numPuntVisual=substr_count($cadaux, "V");
	$sql="UPDATE mdl_user_data as d";
	$sql.="INNER JOIN mdl_user_info_field as f ON f.id=d.fieldid AND f.shortname='Visual'";
	$sql.="SET d.data=".$numPuntVisual;
	$sql.="WHERE d.userid".$USER->id;
	if(!$DB->execute($sql,null)) echo "error";

	$numPuntAuditivo=substr_count($cadaux, "A");
	$sql="UPDATE mdl_user_data as d";
	$sql.="INNER JOIN mdl_user_info_field as f ON f.id=d.fieldid AND f.shortname='AUDITIVO'";
	$sql.="SET d.data=".$numPuntAuditivo;
	$sql.="WHERE d.userid".$USER->id;
	if(!$DB->execute($sql,null)) echo "error";

	$numPuntLeerEscribir=substr_count($cadaux, "R");
	$sql="UPDATE mdl_user_data as d";
	$sql.="INNER JOIN mdl_user_info_field as f ON f.id=d.fieldid AND f.shortname='LeerEscribir'";
	$sql.="SET d.data=".$numPuntLeerEscribir;
	$sql.="WHERE d.userid".$USER->id;
	if(!$DB->execute($sql,null)) echo "error";

	$numPuntKinestesico=substr_count($cadaux, "K");
	$sql="UPDATE mdl_user_data as d";
	$sql.="INNER JOIN mdl_user_info_field as f ON f.id=d.fieldid AND f.shortname='Kinestesico'";
	$sql.="SET d.data=".$numPuntKinestesico;
	$sql.="WHERE d.userid".$USER->id;
	if(!$DB->execute($sql,null)) echo "error";

	$EA="LeerEscribir";
	$maxPunt=$numPuntLeerEscribir;
	if($numPuntVisual>$maxPunt){$EA="Visual"; $maxPunt=$numPuntVisual;}
	if($numPuntAuditivo>$maxPunt){$EA="Auditivo"; $maxPunt=$numPuntAuditivo;}
	if($numPuntKinestesico>$maxPunt){$EA="Kinestesico"; $maxPunt=$numPuntKinestesico;}

	$sql="UPDATE mdl_user_data as d";
	$sql.="INNER JOIN mdl_user_info_field as f ON f.id=d.fieldid AND f.shortname='EA'";
	$sql.="SET d.data='$EA' ";
	$sql.="WHERE d.userid".$USER->id;
	if(!$DB->execute($sql,null)) echo "error";
	profile_load_custom_fields($USER);

	redirect($CFG->httpswwwroot.'/index.php');

}

?>
<h1> Cuestionario VARK </h1>
<form name="cuestionarioVARK" method="POST">
	1.Está ayudando a una persona que desea ir al aeropuerto, al centro de la ciudad o a la estación del ferrocarril. Ud.: <br><br>
	<input type="checkbox"  name="resp[]" value="K"> iría con ella.<br>
	<input type="checkbox"  name="resp[]" value="A"> le diría cómo llegar.<br>
	<input type="checkbox"  name="resp[]" value="R"> le daría las indicaciones por escrito (sin un mapa).<br>
	<input type="checkbox"  name="resp[]" value="V"> le daría un mapa.<br>

	2. No estás seguro si una palabra se escribe como: transcendente o tracendente. Ud: <br><br>
	<input type="checkbox"  name="resp[]" value="V"> vería las palabras en tu mente y elegiría la que mejor luce.<br>
	<input type="checkbox"  name="resp[]" value="A"> pensaría en como suena cada palabra y elegiría la que mejor luce.<br>
	<input type="checkbox"  name="resp[]" value="R"> la buscaría en un diccionario.<br>
	<input type="checkbox"  name="resp[]" value="K"> escribiría ambas palabras y elegiría una.<br>

	3. Está planeando unas vacaciones para un grupo de personas y desearía la retroalimentación de ellos sobre el plan. Ud.: <br><br>
	<input type="checkbox"  name="resp[]" value="K"> describiría algunos de los atractivos del viaje.<br>
	<input type="checkbox"  name="resp[]" value="V"> utilizaría un mapa o un sitio web para mostrar los lugares.<br>
	<input type="checkbox"  name="resp[]" value="R"> les daría una copia del itinerario impreso..<br>
	<input type="checkbox"  name="resp[]" value="A"> les llamaría por teléfono, les escribiría o les enviaría un e-mail.<br>

	4. Va a cocinar algún platillo especial para su familia. Ud.: <br><br>
	<input type="checkbox"  name="resp[]" value="K"> describiría algunos de los atractivos del viaje.<br>
	<input type="checkbox"  name="resp[]" value="V"> utilizaría un mapa o un sitio web para mostrar los lugares.<br>
	<input type="checkbox"  name="resp[]" value="R"> les daría una copia del itinerario impreso..<br>
	<input type="checkbox"  name="resp[]" value="A"> les llamaría por teléfono, les escribiría o les enviaría un e-mail.<br>

	5. Un grupo de turistas desea aprender sobre los parques o las reservas de vida salvaje en su área. Ud.: <br><br>
	<input type="checkbox"  name="resp[]" value="A"> les daría libros o folletos sobre parques o reservas de vida salvaje.<br>
	<input type="checkbox"  name="resp[]" value="V"> les mostraría figuras de Internet, fotografías o libros con imágenes.<br>
	<input type="checkbox"  name="resp[]" value="K"> los llevaría a un parque o reserva y daría una caminata con ellos.<br>
	<input type="checkbox"  name="resp[]" value="R"> les daría una plática acerca de parques o reservas de vida salvaje.<br>

	6. Está a punto de comprar una cámara digital o un teléfono móvil. ¿Además del precio, qué más influye en su decisión? <br><br>
	<input type="checkbox"  name="resp[]" value="K"> lo utiliza o lo prueba.<br>
	<input type="checkbox"  name="resp[]" value="R"> la lectura de los detalles acerca de las características del aparato.<br>
	<input type="checkbox"  name="resp[]" value="V"> el diseño del aparato es moderno y parece bueno.<br>
	<input type="checkbox"  name="resp[]" value="A"> los comentarios del vendedor acerca de las características del aparato.<br>

	7. Recuerde la vez cuando aprendió cómo hacer algo nuevo. Evite elegir una destreza física, como montar bicicleta. ¿Cómo aprendió mejor?: <br><br>
	<input type="checkbox"  name="resp[]" value="K"> viendo una demostración.<br>
	<input type="checkbox"  name="resp[]" value="A"> escuchando la explicación de alguien y haciendo preguntas.<br>
	<input type="checkbox"  name="resp[]" value="V"> siguiendo pistas visuales en diagramas y gráficas.<br>
	<input type="checkbox"  name="resp[]" value="R"> siguiendo instrucciones escritas en un manual o libro de texto.<br>

	8. Tiene un problema con su rodilla. Preferiría que el doctor: <br><br>
	<input type="checkbox"  name="resp[]" value="R"> le diera una dirección web o algo para leer sobre el asunto..<br>
	<input type="checkbox"  name="resp[]" value="K"> utilizara el modelo plástico de una rodilla para mostrarle qué está mal.<br
	<input type="checkbox"  name="resp[]" value="A"> le describiera qué está mal.<br>
	<input type="checkbox"  name="resp[]" value="V"> le mostrara con un diagrama qué es lo que está mal.<br>

	9. Desea aprender un nuevo programa, habilidad o juego de computadora. Ud. debe: <br><br>
	<input type="checkbox"  name="resp[]" value="R"> leer las instrucciones escritas que vienen con el programa.<br>
	<input type="checkbox"  name="resp[]" value="A"> platicar con personas que conocen el programa.<br>
	<input type="checkbox"  name="resp[]" value="K"> utilizar los controles o el teclado.<br>
	<input type="checkbox"  name="resp[]" value="V"> seguir los diagramas del libro que vienen con el programa.<br>

	10. Le gustan los sitios web que tienen: <br><br>
	<input type="checkbox"  name="resp[]" value="K"> cosas que se pueden picar, mover o probar.<br>
	<input type="checkbox"  name="resp[]" value="V"> descripciones escritas interesantes, características y explicaciones..<br>
	<input type="checkbox"  name="resp[]" value="R"> un diseño interesante y características visuales.<br>
	<input type="checkbox"  name="resp[]" value="A"> canales de audio para oír música, programas o entrevistas.<br>

	11. Además del precio, ¿qué influiría más en su decisión de comprar un nuevo libro de no ficción? <br><br>
	<input type="checkbox"  name="resp[]" value="V"> la apariencia le resulta atractiva.<br>
	<input type="checkbox"  name="resp[]" value="R"> una lectura rápida de algunas partes del libro.<br>
	<input type="checkbox"  name="resp[]" value="A"> un amigo le habla del libro y se lo recomienda.<br>
	<input type="checkbox"  name="resp[]" value="K"> tiene historias, experiencias y ejemplos de la vida real.<br>

	12. Está utilizando un libro, CD o sitio web para aprender cómo tomar fotografías con su nueva cámara digital. Le gustaría tener: <br><br>
	<input type="checkbox"  name="resp[]" value="A"> la oportunidad de hacer preguntas y que le hablen sobre la cámara y sus características.<br>
	<input type="checkbox"  name="resp[]" value="R"> instrucciones escritas con claridad, con características y puntos sobre qué hacer.<br>
	<input type="checkbox"  name="resp[]" value="V"> diagramas que muestren la cámara y qué hace cada una de sus partes.<br>
	<input type="checkbox"  name="resp[]" value="K"> muchos ejemplos de fotografías buenas y malas y cómo mejorar éstas.<br>

	13.Prefiere a un profesor o un expositor que utiliza: <br><br>
	<input type="checkbox"  name="resp[]" value="K"> demostraciones, modelos o sesiones prácticas.<br>
	<input type="checkbox"  name="resp[]" value="A"> preguntas y respuestas, charlas, grupos de discusión u oradores invitados.<br>
	<input type="checkbox"  name="resp[]" value="R"> folletos, libros o lecturas.<br>
	<input type="checkbox"  name="resp[]" value="V"> diagramas, esquemas o gráficas.<br>

	14.Ha acabado una competencia o una prueba y quisiera una retroalimentación. Quisiera tener la retroalimentación: <br><br>
	<input type="checkbox"  name="resp[]" value="K"> utilizando ejemplos de lo que ha hecho.<br>
	<input type="checkbox"  name="resp[]" value="R"> utilizando una descripción escrita de sus resultados.<br>
	<input type="checkbox"  name="resp[]" value="A"> escuchando a alguien haciendo una revisión detallada de su desempeño.<br>
	<input type="checkbox"  name="resp[]" value="V"> utilizando gráficas que muestren lo que ha conseguido.<br>

	15. Va a elegir sus alimentos en un restaurante o café. Ud.: <br><br>
	<input type="checkbox"  name="resp[]" value="K"> elegiría algo que ya ha probado en ese lugar.<br>
	<input type="checkbox"  name="resp[]" value="A"> escucharía al mesero o pediría recomendaciones a sus amigos.<br>
	<input type="checkbox"  name="resp[]" value="R"> elegiría a partir de las descripciones del menú.<br>
	<input type="checkbox"  name="resp[]" value="V"> observaría lo que otros están comiendo o las fotografías de cada platillo.<br>

	16.Tiene que hacer un discurso importante para una conferencia o una ocasión especial. Ud.: <br><br>
	<input type="checkbox"  name="resp[]" value="V"> elaboraría diagramas o conseguiría gráficos que le ayuden a explicar las ideas.<br>
	<input type="checkbox"  name="resp[]" value="A"> escribiría algunas palabras clave y práctica su discurso repetidamente.<br>
	<input type="checkbox"  name="resp[]" value="R"> escribiría su discurso y se lo aprendería leyéndolo varias veces.<br>
	<input type="checkbox"  name="resp[]" value="K"> conseguiría muchos ejemplos e historias para hacer la charla real y práctica.<br>
<?php
echo $OUTPUT->footer();
?>

</html>


