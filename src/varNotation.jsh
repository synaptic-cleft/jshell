void write(Cat c) throws Exception {    try {
        var fileOut = new FileOutputStream("/tmp/cat.ser");
	var out = new ObjectOutputStream(fileOut);

        out.writeObject(c);
        out.close();
        fileOut.close();
        System.out.println("Serialized cat is saved in /tmp/cat.ser");
    } catch (Exception ignored) {}
}

void read() {
//compiler wouldn't know which type c should be
Cat c = null;
try {
	var fileIn = new FileInputStream("/tmp/cat.ser");
	var in = new ObjectInputStream(fileIn);

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

class Cat implements java.io.Serializable {
//cannot type infer variables without having them assigned
	String name;
	int age;
}

//naming of variables becomes more important when using var
var cat = new Cat();
cat.name = "Luna"
cat.age = 101
write(cat)
read()









//Now let's restrict the deserialization. We only want to deserialize small cats. The big ones are dangerous. They would eat all your food and you would possibly starve to death!
//ObjectInputFilter.Config.setSerialFilter(ObjectInputFilter.Config.createFilter("maxbytes=50"));