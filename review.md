# Overall code review

* Authentication filter is just fantastic 🎉
* Really cool module separation using `.gitmodules`
* The project is super large and congratulations on making it all work


I will share some best practices that I have learned over time. Hope that you find them useful:

* You don’t need to have an interface for all classes, only for these which can be extended over time. Some good examples from your code:
  * `Filter -> AuthenticationFilter`
  * `FilterChain -> CustomFilterChain`


* Util classes can have private constructor which throws an exception. This will not only make it fancy ✨, but also bring some context to the reader who might wonder why this constructor is private:
  ```java
  public final class PasswordEncoder {
      
      private PasswordEncoder() {
          throw new IllegalStateException("Utility class cannot be instantiated");
      }
      
    //...Rest of code
  }
  ```

* `this.` should be called only on several occasions, apart from that it's not needed. [See more](https://stackoverflow.com/questions/2411270/when-should-i-use-this-in-a-class?rq=3)


Client <--> Server architecture:
* You can extract DTO's as a separate module/library and reuse them in client and server as well. That way you can return the DTO objects in controller/servlet, and accept them in your client.

Servlets:
* Return DTO object as a response and work with DTO's in controllers/servlets/services. It's a good practice to not use entity classes directly as it's more error-prone (their behaviour could change unexpectedly somewhere in code, and they can be saved that way in DB)
* I suggest passing the DTO to service methods because you will save a lot of argument parameters, and it will be less error-prone(you can mistakenly replace name parameter with description, etc.) Example from code:
  ```
  productService.updateProduct(prevName, product.name(), product.description(), product.numberInStock(),
  product.minPrice(), product.currPrice(), username));
  ```
* `doDelete` methods: They are perfectly fine, just another best practice is returning `204 NO_CONTENT` as a status code. [See more](https://stackoverflow.com/questions/2342579/http-status-code-for-update-and-delete)

Services:
  * `this.createUser("employee", "employee");` for sensitive data, we should use [env variables](https://www.twilio.com/en-us/blog/set-up-env-variables-intellij-idea-java) so that they are protected from public sources like GitHub.
  * `roleService.findRoleByName(RoleName.CLIENT)` can return `null`. In that case `user.getRoles().add(clientRole);` will throw an NullPointerException
    * You can make all methods named `find<Class>By<Property>` return an `Optional<Class>`. This would always prevent you from missing a potential null value.

Repositories:
* Consistency is key, especially if you are working in a team. You create common ground (rules) and you follow them, so that everyone can read and understand the code. In your project this is not so relevant, but it's a life-saver when the project becomes bigger and bigger. Having said that, I suggest to:
  * Follow the same naming convention across all classes in the same layer. For example `find<Class>By<Property>`.
  * Follow the same return type: `Optional<Class>`
* `void create(T entity) throws HibernateException;` - Returning the created object can be helpful for end-users to see the created resource, especially if it has server-populated fields, such as last-creation-time or ID
* As you reuse most of the SessionFactory functionality across your methods, some helper methods can come in handy:
  ```java
    protected T execute(Function<Session, T> operation) {
        Session session = sessionFactory.openSession();
        T result = operation.apply(session);
        session.close();

        return result;
    }

    protected T executeInTransaction(Function<Session, T> operation) {
        return execute(session -> {
            session.beginTransaction();
            T result = operation.apply(session);
            session.getTransaction().commit();
            return result;
        });
    }
  ```
  You can use them as follows and reduce duplication:
  ```java
    public T create(T entity) throws HibernateException {
        return executeInTransaction(session -> {
            session.persist(entity);
            return entity;
        });
    }

    public Optional<T> findById(UUID id) throws HibernateException {
        return Optional.ofNullable(execute(session -> session.get(this.clazz, id)));
    }
  ```
