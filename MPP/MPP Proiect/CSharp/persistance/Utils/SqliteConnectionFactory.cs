using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;

namespace persistance.Utils{
    public class SqliteConnectionFactory : ConnectionFactory{
        public override IDbConnection CreateConnection(IDictionary<string, string> props){
            string connectionString = props["ConnectionString"];
            Console.WriteLine("SQLite --- se deschide o conexiune la {0}", connectionString);
            return new SQLiteConnection(connectionString);
        }
    }
}