# fluent-validator
Simple functional validator with fluent interface languaje.

# Use
     public static void main(String[] args) throws ValidationException {
        Validator validatorX = new Validator();
        String text = "Hello";
        String emptyText = "";
        String nullText = null;

        List<Integer> list1 = new ArrayList();
        List<Integer> list2 = null;
        List<String> list3 = new ArrayList();
        list3.add("Hola");




        validatorX.notNull(text)
                .withErrorMessage("Es nullText");

        validatorX.notEmptyText(nullText)
                .withErrorMessage("text no puede estar vacío");
        
        //Passing Function
        validatorX.addRuleFor(text)
                .must(s -> "Hello".equals(s))
                .withErrorMessage("Must be equal Hola");
        validatorX.addRuleFor(text)
                .must(String::isEmpty)
                .withErrorMessage("Must be empty");
        
        //Custom
        validatorX.addCustomRule(()-> "Hello".equalsIgnoreCase(text))
                .withErrorMessage("Must be equal Hola");
        

        validatorX.addRuleFor(nullText)
                .andFor("")
                .andFor("TEXT")
                .notNull()
                .and(Constraint.EQUALS("TEXT"))
                .withErrorMessage("error TEXT");

        validatorX.addRuleForValues( list1, list3)
                .andFor(list3)
                .notNull()
                .and(Constraint.NOT_EMPTY_LIST)
                .withErrorMessage("error 1");

        validatorX.addRuleFor(nullText)
                .andFor("")
                .must(Constraint.NOT_NULL)
                .withErrorMessage("error TEXT");

        //Get errors
        validatorX.checkErrorsAndThrow();

        //Throw errors
        List<ErrorValidationDTO> errorValidationDTOS = validatorX.checkErrors();

        
        //Also
       new Validator().notNull(nullText)
                .withErrorMessage("nullText no puede ser nullText")
                .checkErrors();
        
        new Validator().notNull(text)
                .withErrorMessage("nullText")
                .checkErrorsAndThrow();
        new Validator()
                .notEmptyList(list1)
                .and(integers -> integers.get(0)==1 )
                .withErrorMessage("text no puede estar vacío y tiene que ser 1")
                .checkErrorsAndThrow();
    }
    
# Use Static

     public static void main(String[] args) throws ValidationException {
        String text = "Hello";
        String emptyText = "";
        String nullText = null;
        List<Integer> list1 = new ArrayList();
        List<Integer> list2 = null;
        List<String> list3 = new ArrayList();
        list3.add("Hola");

        Validator.createRuleFor(text)
                .must(v -> v.toUpperCase().matches(""))
                .withErrorMessage("no coincide")
                .checkErrorsAndThrow();

        Validator.createRuleFor(text)
                .must(Constraint.NOT_NULL)
                .withErrorMessage("no coincide")
                .checkErrorsAndThrow();

        Validator.createRuleFor(list3)
                .must(Constraint.EMPTY_LIST)
                .withErrorMessage(
                        "La lista no está vacía ")
                .checkErrorsAndThrow();
                
        Validator.createRuleFor(list1)
                .must(Constraint.EMPTY_LIST)
                .withErrorMessage(
                        "La lista está vacía ")
                .checkErrorsAndThrow();
                
        Validator.createRuleFor(list3)
                .must(Constraint.EMPTY_LIST)
                .withErrorMessage(
                        "La lista NO está vacía ")
                .checkErrorsAndThrow();
}

