﻿using System;
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

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Roles>().ToTable("Roles");
        }
    }
}