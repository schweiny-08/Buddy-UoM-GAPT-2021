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
    public class HazardsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public HazardsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Hazards
        [HttpGet("getAllHazards")]
        public async Task<ActionResult<IEnumerable<Hazard>>> GetHazard()
        {
            return await _context.Hazard.ToListAsync();
        }

        // GET: api/Hazards/5
        [HttpGet("getHazardById")]
        public async Task<ActionResult<Hazard>> GetHazard(int id)
        {
            var hazard = await _context.Hazard.FindAsync(id);

            if (hazard == null)
            {
                return NotFound();
            }

            return hazard;
        }

        // PUT: api/Hazards/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("editHazardById")]
        public async Task<IActionResult> PutHazard(int id, Hazard hazard)
        {
            if (id != hazard.hazard_Id)
            {
                return BadRequest();
            }

            _context.Entry(hazard).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!HazardExists(id))
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

        // POST: api/Hazards
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("addHazard")]
        public async Task<ActionResult<Hazard>> PostHazard(Hazard hazard)
        {
            var existingHazard = _context.Hazard.FirstOrDefault(h => h.hazardType == hazard.hazardType);

            if (existingHazard != null && existingHazard.hazardType.Equals(hazard.hazardType, StringComparison.OrdinalIgnoreCase))
                return BadRequest("Hazard with same type already exists!");

            _context.Hazard.Add(hazard);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetHazard", new { id = hazard.hazard_Id }, hazard);
        }

        // DELETE: api/Hazards/5
        [HttpDelete("deleteHazardById")]
        public async Task<IActionResult> DeleteHazard(int id)
        {
            var hazard = await _context.Hazard.FindAsync(id);
            if (hazard == null)
            {
                return NotFound();
            }

            _context.Hazard.Remove(hazard);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool HazardExists(int id)
        {
            return _context.Hazard.Any(e => e.hazard_Id == id);
        }
    }
}
