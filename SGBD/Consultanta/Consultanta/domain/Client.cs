namespace Consultanta.domain{
    public class Client: Entity<int>{
        string nume_c{ get; set; }

        int varsta_c{ get; set; }

        string CNP_c{ get; set; }

        public Client(string nume_c, int varsta_c, string CNP_c){
            this.nume_c = nume_c;
            this.varsta_c = varsta_c;
            this.CNP_c = CNP_c;
        }
    }
}