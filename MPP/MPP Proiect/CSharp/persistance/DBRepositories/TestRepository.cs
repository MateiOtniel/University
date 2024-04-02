using System.Collections.Generic;
using System.Data.SQLite;
using log4net;
using model;
using persistance.Interfaces;
using persistance.Utils;

namespace persistance.DBRepositories{
    public class TestRepository: ITestRepository{
        private IDictionary<string, string> _props;

        private static readonly ILog Log = LogManager.GetLogger("TestRepository");

        public TestRepository(IDictionary<string, string> props){
            this._props = props;
            Log.Info("Creating TestRepository");
        }

        public void Add(Test entity){
            Log.Info("TestRepository Add method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var command = new SQLiteCommand(
                      "insert into Tests values (@id, @idP, @idR, @points, @date_t)",
                      connection as SQLiteConnection)){
                command.Parameters.AddWithValue("@id", null);
                command.Parameters.AddWithValue("@idP", entity.idParticipant);
                command.Parameters.AddWithValue("@idR", entity.idReferee);
                command.Parameters.AddWithValue("@points", entity.points);
                command.Parameters.AddWithValue("@date_t", entity.date);
                command.ExecuteNonQuery();
                Log.InfoFormat("Succesfully added test {0}", entity);
            }
        }


        public void Remove(Test entity){
            RemoveById(entity.id);
        }

        public void RemoveById(int id){
            Log.Info("TestRepository RemoveByID method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var command = new SQLiteCommand("delete from Tests where id = @id",
                      connection as SQLiteConnection)){
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
                Log.InfoFormat("Succesfully removed test with id {0}", id);
            }
        }

        public List<Test> GetAll(){
            var list = new List<Test>();
            Log.Info("TestRepository GetAll method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var cmd = new SQLiteCommand("select * from Tests", connection as SQLiteConnection)){
                using(var reader = cmd.ExecuteReader()){
                    while(reader.Read()){
                        var id = reader.GetInt32(0);
                        var idP = reader.GetInt32(1);
                        var idR = reader.GetInt32(2);
                        var points = reader.GetInt32(3);
                        var date = reader.GetString(4);
                        var test = new Test(idP, idR, points, date);
                        test.id = id;
                        list.Add(test);
                    }
                }
            }

            Log.Info("Successfully created referee's list");
            return list;
        }

        public Test GetById(int id){
            Test test = null;
            Log.Info("TestRepository GetById method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            var cmd = new SQLiteCommand("select * from Tests where id = " + id, connection as SQLiteConnection);
            using(var reader = cmd.ExecuteReader()){
                if(reader.Read()){
                    var idP = reader.GetInt32(1);
                    var idR = reader.GetInt32(2);
                    var points = reader.GetInt32(3);
                    var date = reader.GetString(4);
                    test = new Test(idP, idR, points, date);
                    test.id = id;
                }
            }

            Log.Info("Successfully got test");
            return test;
        }
    }
}