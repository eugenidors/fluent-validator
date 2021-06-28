package org.dorsmedia;

import org.dorsmedia.exceptions.ValidationException;
import org.dorsmedia.validations.Constraint;
import org.dorsmedia.validations.model.ErrorValidation;
import org.dorsmedia.validations.Validator;
import org.dorsmedia.validations.model.Validation;

import java.util.ArrayList;
import java.util.List;

public class ValidatorTest {

    public static void main(String[] args){
        Validator validatorX = new Validator();
        String hola = "Hola";
        String emptyText = "";
        String nulo = null;
        //2 validations on 3 fields

        List<Integer> l1 = new ArrayList();
        List<Integer> l2 = null;
        List<String> l3 = new ArrayList();
        l3.add("Hola");

//        validator.ruleForValues(l1, l2, l3).mustBe().notEmptyList().errorMessage("error 1");
//        validator.ruleForValues(l1, l2, l3).mustBe().notEmptyList2().errorMessage("error 2");
//        validator.ruleForValues(l1, l2, l3).mustBe().notEmptyList3().errorMessage("error 3");
//        validator.ruleForValues(l1, l2, l3).mustBe().notEmptyList4().errorMessage("error 4");

        try {
        Validator.createRuleFor(hola)
                .must(v -> v.toUpperCase().matches(""))
                .withErrorMessage("no coincide")
                .checkErrorsAndThrow();
        } catch (ValidationException e) {
            e.getErrors().forEach(errorValidation -> System.out.println(errorValidation.getMensaje()));
        }
            try {
        Validator.createRuleFor(hola)
                .must(Constraint.NOT_NULL)
                .withErrorMessage("no coincide")
                .checkErrorsAndThrow();
            } catch (ValidationException e) {
                e.getErrors().forEach(errorValidation -> System.out.println(errorValidation.getMensaje()));
            }
        try {
            new Validator().notNull(hola).withErrorMessage("nulo").checkErrorsAndThrow();
        } catch (ValidationException e) {
            e.getErrors().forEach(errorValidation -> System.out.println(errorValidation.getMensaje()));
        }


        validatorX.notNull(hola).withErrorMessage("Es nulo");

        validatorX.addRuleFor(hola)
                .withFieldName("campo hola")
                .notNull()
                .and(h -> h.matches(""))
                .withErrorMessage("Es nulo");



        Validator hola_no_puede_estar_vacío = new Validator().notEmptyText(hola).withErrorMessage("hola no puede estar vacío");
        List<ErrorValidation> nulo_no_puede_ser_nulo = new Validator().notNull(nulo).withErrorMessage("nulo no puede ser nulo").checkErrors();
        List<ErrorValidation> hola_no_puede_estar_vacío1 = new Validator().notEmptyText(nulo).withErrorMessage("hola no puede estar vacío").checkErrors();
        List<ErrorValidation> errorValidations = new Validator().notEmptyList(l1).and(integers -> integers.get(0) == 1)
                .withErrorMessage("hola no puede estar vacío y tiene que ser 1").checkErrors();
        List<ErrorValidation> errorValidations1 = validatorX.addRuleForValues(l1, l3).andFor(l3).notNull()
                .and(Constraint.NOT_EMPTY_LIST).withErrorMessage("error 1").checkErrors();

        print(validatorX.checkErrors());
        validatorX.addRuleFor(nulo).andFor("").andFor("TEXT").notNull().and(Constraint.EQUALS("TEXT")).withErrorMessage("error TEXT");
        print(validatorX.checkErrors());
        validatorX.addRuleFor(nulo).andFor("").must(Constraint.NOT_NULL).withErrorMessage("error TEXT");

        print(validatorX.checkErrors());
//        assert  (validator.checkErrors().size() == 2);
    }


    public void name() {
        Validator validator = new Validator();
        String hola = "Hola";
        String emptyText = "";
        String notEmptyText = "not empty text";

        //Comprobacion errores en compilacion
        validator.addRuleForValues(hola, new String[]{"aaa", "aaaa1"}, "a")
                .must(Constraint.NOT_NULL)
//                .and(Constraint.NOT_EMPTY_TEXT) //no compila porque al poner un String[] ya no se considera una validacion tipo String
//                .and(Constraint.MAX_SIZE(2)) //no compila porque al poner un String[] ya no se considera una validacion tipo String
                .withErrorMessage("Error not null pero no puede validarse not empty con estos valores");
        //Comprobacion errores en compilacion
//        validator.rule(Constraint.NOT_NULL())
//                .and(Constraint.NOT_EMPTY_TEXT) //no compila porque al poner un String[] ya no se considera una validacion tipo String si no de tipo Object
//                .onValues(hola, new String[]{"aaa", "aaaa1"}, "a")
//                .error("Error not null pero no puede validarse not empty con estos valores");

    }

   
    public void testFluentInterface() {
        Validator validator = new Validator();
        String hola = "Hola";
        String emptyText = "";
        String notEmptyText = "not empty text";
        String nulo = null;
        //2 validations on 3 fields
//        validator.rule(Constraint.NOT_NULL())
//                .and(Constraint.NOT_EMPTY_TEXT)
//                .and(Constraint.NOT_NULL())
//                .onValues(nulo, emptyText,
//                        notEmptyText,
//                        "also not empty text")
//                .error("1 [%s] no puede ser nulo");

        assert validator.checkErrors().size() == 2;

//        validator.rule(Constraint.NOT_NULL).onValues(nulo, hola, emptyText).error("999 [%s] no puede ser nulo");
//
//        validator.rule(Constraint.NOT_EMPTY_TEXT)
//                .onValue(hola)
//                .and(nulo)
//                .error("3 [%s] el texto no puede estar vacío");

        //Poniendo primero el valor y luego las validaciones
        validator.addRuleFor(hola)
                .andFor(nulo)
                .andFor("a") //si que cumple
                .must(Constraint.NOT_NULL)
                .and(Constraint.NOT_EMPTY_TEXT)
                .and(Constraint.MAX_LENGTH(2))
                .withErrorMessage("41 [%s] Error de validacion not_null, not_empty, max_size");
        validator.addRuleForValues(hola, nulo, "a")
                .must(Constraint.NOT_NULL)
                .and(Constraint.NOT_EMPTY_TEXT)
                .and(Constraint.MAX_LENGTH(2))
                .withErrorMessage("42 [%s] Error de validacion not_null, not_empty, max_size");


//                .and(Constraint.MAX_SIZE(2)) //no compila porque al poner un String[] ya no se considera una validacion tipo String
        //Comprobacion errores en compilacion
        validator.addRuleForValues(null, "String")
                .must(Constraint.NOT_NULL)
                .and(Constraint.NOT_EMPTY_TEXT) //solo permite poner validaciones de texto
                .withErrorMessage("43 [%s] Error de validacion not_null, not_empty, max_size");


        validator.addRuleFor(hola)
                .must(Constraint.EQUALS("not hola"))
                .or(Constraint.EQUALS("Hola"))
                .withErrorMessage("5 [%s] error no es ni [not hola] ni [Hola]"); //no error si que es hola

        validator.addRuleFor(hola)
                .must(Constraint.EQUALS("not hola"))
                .or(Constraint.EQUALS("also not hola"))
                .withErrorMessage("6 [%s] error no es ni [not hola] ni [also not hola]"); // error ninguna de las dos condicioness se cumple

        validator.addRuleFor(null)
                .must(Constraint.EQUALS("not hola"))
                .or(Constraint.EQUALS("also not hola"))
                .withErrorMessage("6 [%s] error no es ni [not hola] ni [also not hola]");


    }

   
    public void testAdAval() throws Throwable {
        testValidator();
    }

    static void testValidator() throws ValidationException {
        //Realizaremos las validaciones sobre este objeto
String id = "1";

        Validator validator = new Validator();
        //Queremos comprobar que salta error si el id está vacío.
validator.addRuleFor(id).must(Constraint.NOT_NULL).withErrorMessage("El id expediente no puede ser nulo");
        assert  (validator.checkErrors().size() == 1);
        //Con builder
        validator.addRuleFor(id).must(Constraint.NOT_NULL).withErrorMessage("El id expediente no puede ser nulo");
        assert  (validator.checkErrors().size() == 2);
        //Custom validation
        validator.addCustomRule(() -> id != null).withErrorMessage("El id expediente no puede ser nulo");
        assert  (validator.checkErrors().size() == 3);

        //get error list
        List<ErrorValidation> errors = validator.checkErrors();
        System.out.println("Validation errors");
        print(errors);

        //throw error exception
        validator.checkErrorsAndThrow();

    }


    static void testMultipleValidations() {
        //Realizaremos las validaciones sobre este objeto
        String testValue = "Hello Test";

        //Queremos comprobar multiples validaciones en una.
        System.out.println("Testeamos Correcto");
        List<ErrorValidation> validationErrors = new Validator()
                .addRuleFor(testValue)
                .must(ValidatorTest::notEmptyAndEqualsHELLO_TEST) //static method
                //.validate(this::notEmptyAndEqualsHELLO_TEST) //en una clase no estatica tambien valdría esto
                .and(s-> notEmptyAndEqualsHELLO_TEST(s)) //explicit
                .withErrorMessage("no se cumple value!=null && !value.isEmpty() && value.toUpperCase().equals(\"HELLO TEST\");")
                .checkErrors();
        print(validationErrors);


        System.out.println("Testeamos SIZE");
        List<ErrorValidation> validationErrors2 = new Validator()
                .addRuleFor(testValue).must(Constraint.NOT_EMPTY_TEXT
                                .and(Constraint.MAX_LENGTH(1))).withErrorMessage(
                        "ERROR EN EL SIZE")
                .checkErrors();
        print(validationErrors2);






        String idExpediente = "hola";
        System.out.println("Testeamos Or dos condiciones erroneas");
        List<ErrorValidation> orErrors2 = new Validator()
                .addRuleFor(testValue)
                .andFor(idExpediente)
                .must(Constraint.EQUALS("NOT Hello Test"))
                .or(Constraint.EQUALS("ALSO NOT Hello Test"))
                .withErrorMessage("Error equals")
                .checkErrors();

        Integer intValue = 3;
        Validator tiene_que_ser_nulo = Validator.createRuleFor(3).mustBeNull().withErrorMessage("tiene que ser nulo");
        List<ErrorValidation> error = Validator.createRuleFor(3).mustBeNull().withErrorMessage("error").checkErrors();
        Validator validator = new Validator();
        validator.addRuleFor(idExpediente).notNull().withErrorMessage("id no puede ser nulo");
        validator.addCustomRule(() -> 1==1).withErrorMessage("siempre pasa");
        validator.addValidation(Validator.createRuleFor(" ").must(Constraint.NOT_EMPTY_TEXT).withErrorMessage("Error"));
        validator.addRuleFor(intValue).must(v -> v<10 ).withErrorMessage(" Debe ser menor de 10");
        validator.addRuleFor(intValue).must(Constraint.NOT_NULL).withErrorMessage("no puede ser nulo");
        validator.notNull(intValue).withErrorMessage("no puede ser nulo");
        //De manera estatica
        Validator.createRuleFor(intValue).notNull().withErrorMessage("no puede ser nulo").checkErrors();

        String hola = "Hola";
        validator.addRuleFor(hola).must(s -> s.toUpperCase().equals("HOLA"))
                .withErrorMessage("Hola tiene que pasar la validación especifica");
        validator.addValidation(Validator.createRuleFor(hola).must(s -> s.toUpperCase().equals("HOLA"))
                .withErrorMessage("Hola tiene que pasar la validación especifica"));

        validator.addValidation(new Validation() {
            @Override
            public boolean isValid() {
                return hola.toUpperCase().equals("HOLA");
            }
            @Override
            public String getErrorMessage() {
                return "Hola tiene que pasar la validación especifica";
            }
            @Override
            public String getValueName() {
                return hola;
            }
        });
        
        print(orErrors2);

    }

    private static boolean notEmptyAndEqualsHELLO_TEST(String value) {
        return value!=null && !value.isEmpty() && value.toUpperCase().equals("HELLO TEST");
    }



    private static void print(Object o) {
        System.out.println(o);
    }
}
