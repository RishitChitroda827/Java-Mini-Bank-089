# MiniBank

MiniBank is a console-based core banking application developed across 14 lab practicals covering Object-Oriented Programming and advanced Java concepts.

## Project Structure

```
OOP-MiniBank-089/
├── exception/
│   ├── AccountNotFoundException.java
│   ├── BankException.java
│   ├── InsufficientFundsException.java
│   └── InvalidAmountException.java
├── model/
│   ├── Account.java
│   ├── BankInterfaces.java
│   ├── CurrentAccount.java
│   ├── Customer.java
│   ├── FixedDepositAccount.java
│   ├── MiniBank.java
│   ├── SavingsAccount.java
│   └── annotation/
│       ├── Audit.java
│       ├── Id.java
│       ├── MaxLength.java
│       └── Positive.java
├── service/
│   ├── AccountStore.java
│   ├── AccountWorker.java
│   ├── BankService.java
│   ├── TransactionBuffer.java
│   └── TransactionProcessor.java
├── test/
│   ├── MiniBankTest.java
│   └── Test.java
├── util/
│   ├── AnnotationValidator.java
│   ├── BankInfo.java
│   ├── ReportGenerator.java
│   ├── Repository.java
│   ├── StatePersister.java
│   ├── StatementFormatter.java
│   ├── TransactionLog.java
│   ├── TransactionSession.java
│   └── Validator.java
├── manifest.txt
├── MiniBank.jar
└── README.md
```

## Features by Practical

- **Practical 1–3**: Foundation classes (`Account`, `Customer`), basic menu shell, encapsulation, `equals`, `hashCode`, and `toString`.
- **Practical 4**: Regular expression validation (`Validator`), string manipulation (`StatementFormatter`).
- **Practical 5**: Abstract `Account` class and specialized account subclasses (`SavingsAccount`, `CurrentAccount`, `FixedDepositAccount`).
- **Practical 6**: Package organization (`model`, `util`, `service`), interface hierarchy (`Transactable`, `InterestBearing`), lambda expressions and runnable JAR packaging.
- **Practical 7**: Metadata and custom annotations (`@Id`, `@Positive`, `@MaxLength`) with reflection-based validator (`AnnotationValidator`).
- **Practical 8**: Checked custom exception hierarchy (`BankException`, `InsufficientFundsException`, `InvalidAmountException`, `AccountNotFoundException`) and try-with-resources with `AutoCloseable`.
- **Practical 9**: Multithreading with `AccountWorker`, observing race conditions, thread synchronization (`synchronized`).
- **Practical 10**: Managed thread pool (`TransactionProcessor`), producer-consumer pattern using `wait()` and `notify()`, and deadlock prevention using ordered locking (`safeTransfer`).
- **Practical 11**: Serialization (`StatePersister`), append-only logging (`TransactionLog`), and NIO directory reporting (`ReportGenerator`).
- **Practical 12**: Collections framework (`AccountStore`), sorting accounts with `Comparator`, `Comparable<Account>`, and `ConcurrentHashMap`.
- **Practical 13**: Generic reflection repository (`Repository<T>`), `@SafeVarargs`, and bounded generic methods (`average`).
- **Practical 14**: Service layer orchestration (`BankService`), interactive menu integration, persistent startup and shutdown, audit logging (`@Audit`), and unit testing (`MiniBankTest`).

## Compilation and Execution

### Compile
```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

### Run Application
```powershell
java -cp out model.MiniBank
```

### Run Unit Tests
```powershell
java -cp out test.MiniBankTest
```

### Build & Run JAR
```powershell
jar cfm MiniBank.jar manifest.txt -C out .
java -jar MiniBank.jar
```
