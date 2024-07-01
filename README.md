# Social Plans Management (SPM)

It is an application for the organization of social plans among a group of users.

The application will allow users to create, join, rate and manage social plans, which consist of a series of consecutive
activities, via console.

### Architecture

`Three Tier`

### Dependencies

`Maven` `Spring-boot` `Spring-data` `Spring-test` `Lombok` `PostgreSQL` `H2 Database` `Log4j`

### Design Patterns

`Dependency injection (by Spring)` `Builder (by Lombok)` `Command`

### Notes

#### How to configure `spring.datasource.url=${DATABASE_URL}` from `application-prod.properties` file:

##### macOS and Linux

1. Terminal: Open a terminal window.
2. Edit file `.bash_profile` if you use Bash or `.zshrc` if you use Zsh

```
    vi ~/.bash_profile  # For Bash
```

```
    vi ~/.zshrc  # For Zsh
```

3. Add the Environment Variable:

```
    export DATABASE_URL=your_database_url_here
```

Example:

```
    export DATABASE_URL=jdbc:mysql://localhost:3306/db
```

4. Save the changes and close the editor. Then, run the `source` command to apply the changes to the current session:

```
    source ~/.bash_profile  # For Bash
```

```
    source ~/.zshrc  # For Zsh
```

##### Windows

1. Open Command Prompt: Press Win + R, type cmd, and press Enter to open the Command Prompt.
2. Set the Environment Variable:

```
    setx DATABASE_URL "your_database_url_here"
```

3. Close Command Prompt: Close the Command Prompt window after executing the command.