using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BuddyAPI.Data;
using BuddyAPI.Models;

namespace BuddyAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BuildingsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public BuildingsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Buildings
        [HttpGet("getAllBuildings")]
        public async Task<ActionResult<IEnumerable<Buildings>>> GetBuildings()
        {
            return await _context.Buildings.ToListAsync();
        }

        // GET: api/Buildings/5
        [HttpGet("getBuildingById")]
        public async Task<ActionResult<Buildings>> GetBuildings(int id)
        {
            var buildings = await _context.Buildings.FindAsync(id);

            if (buildings == null)
            {
                return NotFound();
            }

            return buildings;
        }

        [HttpGet("getBuildingByName")]
        public async Task<ActionResult<Buildings>> GetBuildingsByName(string name) {
            var building =  _context.Buildings.FirstOrDefault( b => b.buildingName == name);

            if (building == null)
                return NotFound();

            return building;

        }

        // PUT: api/Buildings/5
        [HttpPut("editBuildingById")]
        public async Task<IActionResult> PutBuildings(int id, Buildings buildings)
        {
            if (id != buildings.building_Id)
            {
                return BadRequest();
            }

            _context.Entry(buildings).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!BuildingsExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Buildings
        [HttpPost("addBuilding")]
        public async Task<ActionResult<Buildings>> PostBuildings(Buildings buildings)
        {
            _context.Buildings.Add(buildings);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetBuildings", new { id = buildings.building_Id }, buildings);
        }

        // DELETE: api/Buildings/5
        [HttpDelete("deleteBuildingById")]
        public async Task<IActionResult> DeleteBuildings(int id)
        {
            var buildings = await _context.Buildings.FindAsync(id);
            if (buildings == null)
            {
                return NotFound();
            }

            _context.Buildings.Remove(buildings);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool BuildingsExists(int id)
        {
            return _context.Buildings.Any(e => e.building_Id == id);
        }
    }
}
