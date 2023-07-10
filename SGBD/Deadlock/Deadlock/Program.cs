using System;
using System.Data;
using System.Data.SqlClient;
using System.Threading;

namespace Deadlock{
    class DeadlockClass{
        private const string ConnectionString =
            "Server=DESKTOP-8PPMB1T;Database=ConsultantaFinanciara;Integrated Security=true;TrustServerCertificate=true;";

        public void Run(){
            ThreadStart deadlock1 = Deadlock1;
            ThreadStart deadlock2 = Deadlock2;
            Thread thread1 = new Thread(deadlock1);
            Thread thread2 = new Thread(deadlock2);
            thread1.Start();
            thread2.Start();
        }

        private void Deadlock1(){ Deadlock("Deadlock1"); }

        private void Deadlock2(){ Deadlock("Deadlock2"); }

        private void Deadlock(string procedureName){
            SqlConnection connection = new SqlConnection(ConnectionString);
            Console.WriteLine(procedureName + " begin...");
            SqlCommand cmd = new SqlCommand(procedureName, connection);
            cmd.CommandType = CommandType.StoredProcedure;
            connection.Open();
            var rowsAffected = 0;
            try{
                rowsAffected = cmd.ExecuteNonQuery();
                Console.WriteLine(procedureName + " finished, rows affected: " +
                                  rowsAffected);
                connection.Close();
            } catch(SqlException){
                Console.WriteLine(procedureName + " failed!\nRetrying...\n");
                var tries = 3;
                while(rowsAffected < 2 && tries > 0){
                    Console.WriteLine(procedureName + " failed! rows affected: " +
                                      rowsAffected + " tries: " + tries);
                    try{
                        rowsAffected = cmd.ExecuteNonQuery();
                        Console.WriteLine(procedureName +
                                          " finished, rows affected: " +
                                          rowsAffected + " and " + tries +
                                          " tries remaining");
                        connection.Close();
                        tries = 0;
                    } catch(SqlException e){
                        if(e.Number != 1205){
                            Console.WriteLine("Transaction has failed!");
                            break;
                        }
                        tries--;
                        Console.WriteLine(procedureName + " failed, " + tries +
                                          " remaining");
                    }
                }
            }
        }
    }

    internal class Program{
        public static void Main(){
            DeadlockClass deadlockClass = new DeadlockClass();
            deadlockClass.Run();
        }
    }
}