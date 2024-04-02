using System.Collections.Generic;
using System.Data.SQLite;
using log4net;
using model;
using persistance.Interfaces;
using persistance.Utils;

namespace persistance.DBRepositories{
    public class ParticipantRepository: IParticipantRepository{
        private static readonly ILog log =
            LogManager.GetLogger("ParticipantRepository");

        private IDictionary<string, string> _props;

        public ParticipantRepository(IDictionary<string, string> props){
            this._props = props;
            log.Info("Creating ParticipantRepository");
        }

        public void Add(Participant entity){
            log.Info("ParticipantRepository Add method");
            log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var command = new SQLiteCommand(
                      "insert into Participants values (@id, @name, @totalPoints)",
                      connection as SQLiteConnection)){
                command.Parameters.AddWithValue("@id", null);
                command.Parameters.AddWithValue("@name", entity.Name);
                command.Parameters.AddWithValue("@totalPoints", entity.TotalPoints);
                command.ExecuteNonQuery();
                log.InfoFormat("Succesfully added participant {0}", entity);
            }
        }


        public void Remove(Participant entity){
            log.Info("ParticipantRepository RemoveByID method");
            RemoveById(entity.id);
        }

        public void RemoveById(int id){
            log.Info("ParticipantRepository RemoveByID method");
            log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var command = new SQLiteCommand(
                      "delete from Participants where id_p = @id",
                      connection as SQLiteConnection)){
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
                log.InfoFormat("Succesfully removed participant with id {0}", id);
            }
        }

        public List<Participant> GetAll(){
            var list = new List<Participant>();
            log.Info("ParticipantRepository GetAll method");
            log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var cmd = new SQLiteCommand("select * from Participants",
                      connection as SQLiteConnection)){
                using(var reader = cmd.ExecuteReader()){
                    while(reader.Read()){
                        var id = reader.GetInt32(0);
                        var name = reader.GetString(1);
                        var totalPoints = reader.GetInt32(2);
                        var participant = new Participant(name, totalPoints);
                        participant.id = id;
                        list.Add(participant);
                    }
                }
            }

            log.Info("Successfully created participant's list");
            return list;
        }

        public Participant GetById(int id){
            Participant participant = null;
            log.Info("ParticipantRepository GetById method");
            log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            var cmd = new SQLiteCommand(
                "select * from Participants where id_p = " + id,
                connection as SQLiteConnection);
            using(var reader = cmd.ExecuteReader()){
                if(reader.Read()){
                    var name = reader.GetString(1);
                    var totalPoints = reader.GetInt32(2);
                    participant = new Participant(name, totalPoints);
                    participant.id = id;
                }
            }

            log.Info("Successfully got participant");
            return participant;
        }

        public void Update(Participant participant){
            log.Info("ParticipantRepository Update method");
            log.Info("Get database connection");
            var connection = DbUtils.GetConnection(_props);
            using(var command = new SQLiteCommand(
                      "update Participants set name = @name, totalPoints = @totalPoints where id_p = @id",
                      connection as SQLiteConnection)){
                command.Parameters.AddWithValue("@id", participant.id);
                command.Parameters.AddWithValue("@name", participant.Name);
                command.Parameters.AddWithValue("@totalPoints",
                    participant.TotalPoints);
                command.ExecuteNonQuery();
                log.InfoFormat("Succesfully updated participant {0}", participant);
            }
        }
    }
}