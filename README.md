# BlockChain

This program emulates the behaviour of a simple Block Chain.
It creates a hundred miners(threads) that run in parallel competing to add a Block to the chain.

* **Structure**:
   
   The app has to main parts:
   
   * *The Miners*: They represent external software that communicates with the app through the Controller API, the only
   information they get about the Block Chain is through the BlockChainDTO simplified data. They have a mining 
   algorithm to try to add a new Block to the chain.
   
   * *The BlockChain app*: It is composed by the **Blocks**, a data class with no logic implementation, 
   the **BlockChain** that functions as a data structure containing Blocks, the **Auditor** that implements 
   the validation logic and the **FileManager** that performs the IO operations.

   I have tried to stick to the SOLID principles. The higher level classes (The Controller) and the lower level
   classes are linked through interfaces, with no strict dependencies.
   
   ![alt text](https://github.com/ramiro-igmun/BlockChain-Test/blob/master/BlockChain.png "UML")


* **Running The App**:

   You can run the app from the command line after installing Maven on your computer with the following command
   from the project folder:

   ```
   mvn exec:java -Dexec.mainClass=BlockChainInit
   ```
   
   Press `Ctrl+c` to stop it.
   
   ![alt text](https://github.com/ramiro-igmun/BlockChain-Test/blob/master/blockchain2.gif "Block Chain")
