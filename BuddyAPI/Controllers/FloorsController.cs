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
    public class FloorsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public FloorsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Floors
        [HttpGet("getAllFloors")]
        public async Task<ActionResult<IEnumerable<Floor>>> GetFloor()
        {
            return await _context.Floor.ToListAsync();
        }

        // GET: api/Floors/5
        [HttpGet("getFloorById")]
        public async Task<ActionResult<Floor>> GetFloor(int id)
        {
            var floor = await _context.Floor.FindAsync(id);

            if (floor == null)
            {
                return NotFound();
            }

            return floor;
        }

        //GET: Floor via floor level
        [HttpGet("getFloorByLevel")]
        public async Task<ActionResult<Floor>> GetFloorByLevel(int level) {
            var floor = _context.Floor.FirstOrDefault(f => f.floorLevel == level);

            if (floor == null)
                return NotFound();

            return floor;
        }

        // PUT: api/Floors/5
        [HttpPut("editFloorById")]
        public async Task<IActionResult> PutFloor(int id, Floor floor)
        {
            if (id != floor.floor_Id)
            {
                return BadRequest();
            }

            _context.Entry(floor).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!FloorExists(id))
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

        // POST: api/Floors
        [HttpPost("addFloor")]
        public async Task<ActionResult<Floor>> PostFloor(Floor floor)
        {
            var existingFloor = _context.Floor.FirstOrDefault(f => f.floorLevel == floor.floorLevel);

            if (existingFloor != null && existingFloor.building_Id == floor.building_Id)
                // Floor in building already has same level
                return BadRequest("Floor with same level in building already exists!");

            _context.Floor.Add(floor);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetFloor", new { id = floor.floor_Id }, floor);
        }

        // DELETE: api/Floors/5
        [HttpDelete("deleteFloorById")]
        public async Task<IActionResult> DeleteFloor(int id)
        {
            var floor = await _context.Floor.FindAsync(id);
            if (floor == null)
            {
                return NotFound();
            }

            _context.Floor.Remove(floor);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool FloorExists(int id)
        {
            return _context.Floor.Any(e => e.floor_Id == id);
        }
    }
}
