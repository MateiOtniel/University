namespace Consultanta.domain{
    public class Meeting{
        public int cod_c{ get; set; }

        public int cod_i{ get; set; }

        public string etapa{ get; set; }

        public Meeting(int cod_c, int cod_i, string etapa){
            this.cod_c = cod_c;
            this.cod_i = cod_i;
            this.etapa = etapa;
        }

        public override string ToString(){
            return etapa;
        }
    }
}