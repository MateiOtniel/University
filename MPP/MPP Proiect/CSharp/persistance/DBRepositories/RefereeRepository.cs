using System.Collections.Generic;
using System.Data.SQLite;
using log4net;
using model;
using persistance.exception;
using persistance.Interfaces;
using persistance.Utils;

namespace persistance.DBRepositories{
    public class RefereeRepository: IRefereeRepository{
        private IDictionary<string, string> _props;

        private static readonly ILog Log = LogManager.GetLogger("RefereeRepository");

        public RefereeRepository(IDictionary<string, string> props){
            this._props = props;
            Log.Info("Creating ParticipantRepository");
        }

        public void Add(Referee entity){
            Log.Info("RefereeRepository Add method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var command = new SQLiteCommand(
                      "insert into Referees values (@id, @name, @email, @password)",
                      connection as SQLiteConnection)){
                command.Parameters.AddWithValue("@id", null);
                command.Parameters.AddWithValue("@name", entity.Name);
                command.Parameters.AddWithValue("@email", entity.Email);
                command.Parameters.AddWithValue("@password", entity.Password);
                command.ExecuteNonQuery();
                Log.InfoFormat("Succesfully added referee {0}", entity);
            }
        }


        public void Remove(Referee entity){ RemoveById(entity.id); }

        public void RemoveById(int id){
            Log.Info("RefereeRepository RemoveByID method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var command = new SQLiteCommand(
                      "delete from Referees where id_r = @id",
                      connection as SQLiteConnection)){
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
                Log.InfoFormat("Succesfully removed referee with id {0}", id);
            }
        }

        public List<Referee> GetAll(){
            var list = new List<Referee>();
            Log.Info("RefereeRepository GetAll method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var cmd = new SQLiteCommand("select * from Referees",
                      connection as SQLiteConnection)){
                using(var reader = cmd.ExecuteReader()){
                    while(reader.Read()){
                        var id = reader.GetInt32(0);
                        var name = reader.GetString(1);
                        var email = reader.GetString(2);
                        var password = reader.GetString(3);
                        var referee = new Referee(name, email, password);
                        referee.id = id;
                        list.Add(referee);
                    }
                }
            }

            Log.Info("Successfully created referee's list");
            return list;
        }

        public Referee GetById(int id){
            Referee referee = null;
            Log.Info("RefereeRepository GetById method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            var cmd = new SQLiteCommand("select * from Referees where id_r = " + id,
                connection as SQLiteConnection);
            using(var reader = cmd.ExecuteReader()){
                if(reader.Read()){
                    var name = reader.GetString(1);
                    var email = reader.GetString(2);
                    var password = reader.GetString(3);
                    referee = new Referee(name, email, password);
                    referee.id = id;
                }
            }

            Log.Info("Successfully got referee");
            return referee;
        }

        public Referee GetRefereeByEmailAndPassword(string email, string password){
            Referee referee = null;
            Log.Info("RefereeRepository GetRefereeByEmailAndPassword method");
            Log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            var cmd = new SQLiteCommand(
                "select * from Referees where email = '" + email +
                "' and password = '" + password + "'", connection as SQLiteConnection);
            using(var reader = cmd.ExecuteReader()){
                if(reader.Read()){
                    var id = reader.GetInt32(0);
                    var name = reader.GetString(1);
                    referee = new Referee(name, email, password);
                    referee.id = id;
                }
            }
            Log.Info("Successfully got referee");
            return referee;
        }
    }
}