// See https://aka.ms/new-console-template for more information
using Microsoft.Data.SqlClient;

namespace TestingApp{
    internal class Program{
        static void Main(string[] args){
            Console.WriteLine("Merge");
            try{
                string connectionString = @"Server=DESKTOP-8PPMB1T;
                Database=Seminar1;Integrated Security=true;
                TrustServerCertificate=true;";
                using (SqlConnection connection
                       = new SqlConnection(connectionString)){
                    Console.WriteLine("Starea conexiunii: {0}", connection.State);
                    connection.Open();
                    Console.WriteLine("Starea conexiunii: {0}", connection.State);
                    
                    SqlCommand insertCommand = new SqlCommand("insert into Flori (nume, pret, sezon) values "
                                                              + "(@nume, @pret, @sezon);", connection);
                    insertCommand.Parameters.AddWithValue("@nume", "lalea");
                    insertCommand.Parameters.AddWithValue("@pret", 7.0F);
                    insertCommand.Parameters.AddWithValue("@sezon", "primavara");
                    int inertRowCountFlori = insertCommand.ExecuteNonQuery();
                    Console.WriteLine("Insert row count flori: " + inertRowCountFlori);
                }
            }
            catch (Exception ex){
                Console.Error.WriteLine(ex.ToString());
            }
        }
    }
}