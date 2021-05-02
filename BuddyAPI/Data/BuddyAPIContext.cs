using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using BuddyAPI.Models;

namespace BuddyAPI.Data
{
    public class BuddyAPIContext : DbContext
    {

        public BuddyAPIContext(DbContextOptions<BuddyAPIContext> options) : base(options) { }

        public DbSet<Roles> Roles {get; set;}
        public DbSet<Pinpoints> Pinpoints { get; set; }
        public DbSet<PinpointTypes> PinpointTypes { get; set; }
        public DbSet<Floor> Floor { get; set; }
        public DbSet<User> User { get; set; }
        public DbSet<Buildings> Buildings { get; set; }
        public DbSet<Hazard> Hazard { get; set; }
        public DbSet<Itineraries> Itineraries { get; set; }
        public DbSet<Events> Events { get; set; }
        public DbSet<PrivateEvents> PrivateEvents { get; set; }

        /*protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Roles>().ToTable("Roles");
        }*/
    }
}
