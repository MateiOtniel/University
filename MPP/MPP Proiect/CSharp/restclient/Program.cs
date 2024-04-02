using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading;
using System.Threading.Tasks;

namespace restclient{
    internal class Program{
        private static readonly HttpClient Client =
            new HttpClient(new LoggingHandler(new HttpClientHandler()));

        private static readonly string UrlBase = "http://localhost:8080/java/tests";

        public static void Main(string[] args){ RunSync().Wait(); }

        static async Task RunSync(){
            Client.BaseAddress = new Uri(UrlBase);
            Client.DefaultRequestHeaders.Accept.Clear();
            Client.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/json"));
            IEnumerable<Test> tests = await GetAll(UrlBase);
            Console.WriteLine("----------------------------");
            foreach(var t in tests){
                Console.WriteLine(t);
            }
            
  //          Console.WriteLine(tests.Length);
            Test test = new Test(0,12, 4, 300, "2020-10-10");
            await SaveTest(UrlBase, test);
            tests = await GetAll(UrlBase);

            Test addedTest = tests.Last();

            Console.WriteLine(addedTest);
            Console.WriteLine(await GetById(UrlBase, addedTest.v));

            addedTest.id = addedTest.v;
            addedTest.points = 500;
            await Update(UrlBase, addedTest);
            tests = await GetAll(UrlBase);

            Test updatedTest = tests.Last();
            Console.WriteLine(updatedTest);

            await Delete(UrlBase, addedTest.id);

            tests = await GetAll(UrlBase);

        }

        private static async Task Delete(string urlBase, int addedTestId){
            HttpResponseMessage responseMessage = await
                Client.DeleteAsync($"{urlBase}/{addedTestId}");
        }

        private static async Task Update(string urlBase, Test addedTest){
            HttpResponseMessage responseMessage =
                await Client.PutAsJsonAsync($"{urlBase}/{addedTest.id}",
                    addedTest);
        }

        private static async Task<Test> GetById(string path, int id){
            Test test = null;
            HttpResponseMessage response = await Client.GetAsync($"{path}/{id}");
            if(response.IsSuccessStatusCode){
                test = await response.Content.ReadAsAsync<Test>();
            }
            return test;
        }

        private static async Task SaveTest(string urlBase, Test test){
            var response = await Client.PostAsJsonAsync(urlBase, test);
            Console.WriteLine("Test saved");
        }

        static async Task<Test[]> GetAll(string path){
            Test[] tests = null;
            HttpResponseMessage response = await Client.GetAsync(path);
            if(response.IsSuccessStatusCode){
                tests = await response.Content.ReadAsAsync<Test[]>();
            }

            return tests;
        }

        public class LoggingHandler: DelegatingHandler{
            public LoggingHandler(HttpMessageHandler innerHandler)
                : base(innerHandler){}

            protected override async Task<HttpResponseMessage> SendAsync(
                HttpRequestMessage request, CancellationToken cancellationToken){
                Console.WriteLine("Request:");
                Console.WriteLine(request.ToString());
                if(request.Content != null){
                    Console.WriteLine(await request.Content.ReadAsStringAsync());
                }

                Console.WriteLine();

                HttpResponseMessage response =
                    await base.SendAsync(request, cancellationToken);

                Console.WriteLine("Response:");
                Console.WriteLine(response.ToString());
                if(response.Content != null){
                    Console.WriteLine(await response.Content.ReadAsStringAsync());
                }

                Console.WriteLine();

                return response;
            }
        }
        public class Entity<ID>{
            public ID id{ set; get; }
            
            public int v{ get; set; }

            public Entity(ID id) { this.id = id; }

            public Entity(int v)
            {
                this.v = v;
            }
            
        }
        
        public class Test : Entity<int>{
            public int idParticipant{ set; get; }

            public int idReferee{ set; get; }

            public int points{ set; get; }
        
            public string date{ set; get; }

            public Test(int id, int idParticipant, int idReferee, int points, string date): base(id){
                this.idParticipant = idParticipant;
                this.idReferee = idReferee;
                this.points = points;
                this.date = date;
            }

            public override string ToString(){
                return idParticipant + "," + idReferee + "," + points + "," + date;
            }
        }
    }
}