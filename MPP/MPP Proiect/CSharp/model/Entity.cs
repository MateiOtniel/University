using System;

namespace model{
    
    [Serializable]
    public class Entity<ID>{
        public ID id{ set; get; }
    }
}