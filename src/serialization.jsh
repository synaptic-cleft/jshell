void write(Cat c) throws Exception {    try {
        FileOutputStream fileOut =
                new FileOutputStream("/tmp/cat.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(c);
        out.close();
        fileOut.close();
        System.out.println("Serialized cat is saved in /tmp/cat.ser");
    } catch (Exception ignored) {}
}

void read() {
Cat c = null;
try {
FileInputStream fileIn = new FileInputStream("/tmp/cat.ser");
ObjectInputStream in = new ObjectInputStream(fileIn);
c = (Cat) in.readObject();
in.close();
fileIn.close();
} catch (Exception e) {
e.printStackTrace();
return;
}
System.out.println("Deserialized cat...");
System.out.println("name: " + c.name);
System.out.println("age: " + c.age);
}








//Oh no, where is the cat anyway? Did you know that JShell allows forward references? So the methods above reference a class that does not even exist yet! Amazing...
//Write the class Cat ;)
//class Cat implements java.io.Serializable {
//String name;
//int age;
//}


//For cat serialization testing:
//Cat c = new Cat();
//c.name = "Luna"
//c.age = 101
//write(c)
//read()









//Now let's restrict the deserialization. We only want to deserialize small cats. The big ones are dangerous. They would eat all your food and you would possibly starve to death!
//ObjectInputFilter.Config.setSerialFilter(ObjectInputFilter.Config.createFilter("maxbytes=50"));