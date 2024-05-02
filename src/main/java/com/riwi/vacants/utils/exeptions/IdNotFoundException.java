package com.riwi.vacants.utils.exeptions;


/*Runtime es la clase general de errores de jaba
 * la ultilizampos para utilizar su constructor y generar errores
 */
public class IdNotFoundException extends RuntimeException{
    private static final  String ERROR_MESSGE = "No hay registros en la entidad %s con el id suminstrado";
    public IdNotFoundException(String nameEntity){

        //Utilizamos el constructor de RuntimeException y enviamos el mensaje 
        //inyectamos el nombre de la entidad
        super( String.format(ERROR_MESSGE, nameEntity));
    }
}
