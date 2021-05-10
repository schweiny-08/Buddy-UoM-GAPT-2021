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
    public class ItinerariesController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public ItinerariesController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Itineraries
        [HttpGet("getAllItineraries")]
        public async Task<ActionResult<IEnumerable<Itineraries>>> GetItineraries()
        {
            return await _context.Itineraries.ToListAsync();
        }

        // GET: api/Itineraries/5
        [HttpGet("getItineraryById")]
        public async Task<ActionResult<Itineraries>> GetItineraries(int id)
        {
            var itineraries = await _context.Itineraries.FindAsync(id);

            if (itineraries == null)
            {
                return NotFound();
            }

            return itineraries;
        }

        // PUT: api/Itineraries/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("editItineraryById")]
        public async Task<IActionResult> PutItineraries(int id, Itineraries itineraries)
        {
            if (id != itineraries.Itinerary_Id)
            {
                return BadRequest();
            }

            _context.Entry(itineraries).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ItinerariesExists(id))
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

        // POST: api/Itineraries
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("addItinerary")]
        public async Task<ActionResult<Itineraries>> PostItineraries(Itineraries itineraries)
        {
            _context.Itineraries.Add(itineraries);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetItineraries", new { id = itineraries.Itinerary_Id }, itineraries);
        }

        // DELETE: api/Itineraries/5
        [HttpDelete("deleteItineraryById")]
        public async Task<IActionResult> DeleteItineraries(int id)
        {
            var itineraries = await _context.Itineraries.FindAsync(id);
            if (itineraries == null)
            {
                return NotFound();
            }

            _context.Itineraries.Remove(itineraries);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool ItinerariesExists(int id)
        {
            return _context.Itineraries.Any(e => e.Itinerary_Id == id);
        }
    }
}
