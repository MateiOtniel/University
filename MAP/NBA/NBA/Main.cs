using NBA.ui;
using NBA.service;

class MainClass {

    static public void Main(string[] args) {
        Service service = new Service();
        UI console = new(service);
        console.Run();
    }
}