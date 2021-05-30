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

       
        //GET: Itinerary via User Id
        [HttpGet("getAllItineraryEventsByUser")]
        public async Task<ActionResult<IEnumerable<Itineraries>>> GetItinerariesByUserId(int userId)
        {
            var itineraries = await _context.Itineraries.Where(i => i.User_Id == userId).ToListAsync();

            if (itineraries == null)
            {
                return NotFound();
            }

            return itineraries;
        }

        //GET: Itinerary via Event & User Id
        [HttpGet("getItineraryEventByUser")]
        public async Task<ActionResult<IEnumerable<Itineraries>>> GetItineraryEventByUserId(int eventId, int userId)
        {
            var itineraries = await _context.Itineraries.Where(i => i.Event_Id == eventId && i.User_Id == userId).ToListAsync();

            if (itineraries == null)
            {
                return NotFound();
            }

            return itineraries;
        }

        //GET: Itinerary via Private Event & User Id
        [HttpGet("getItineraryPrivateEventByUser")]
        public async Task<ActionResult<IEnumerable<Itineraries>>> GetItineraryPrivEventByUserId(int privEventId, int userId)
        {
            var itineraries = await _context.Itineraries.Where(i => i.PrivateEvent_Id == privEventId && i.User_Id == userId).ToListAsync();

            if (itineraries == null)
            {
                return NotFound();
            }

            return itineraries;
        }
        

        // PUT: api/Itineraries/5
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
        [HttpPost("addItinerary")]
        public async Task<ActionResult<Itineraries>> PostItineraries(Itineraries itineraries)
        {
            //Validation check to ensure that not a Private Event & an Event are trying to be added to the database
             if (itineraries.Event_Id != 0 && itineraries.PrivateEvent_Id != 0)
            {
                return BadRequest("You can't add an event and a private event at the same time!");
            }

            //Inputting a new Itinerary for a Private Event
           else if (itineraries.PrivateEvent_Id != 0)
            {
                _context.Itineraries.Add(itineraries);
                await _context.SaveChangesAsync();
                return Ok();
            }
            //Inputting a new Itinerary for a Event
            else if (itineraries.Event_Id != 0)
            {
                //Check to ensure that Event is not already added to the Users Itinerary
                if (this.GetItineraryEventByUserId(itineraries.Event_Id, itineraries.User_Id) != null)
                {
                    _context.Itineraries.Add(itineraries);
                    await _context.SaveChangesAsync();

                    return CreatedAtAction("GetItineraries", new { id = itineraries.Itinerary_Id }, itineraries);
                }
                else
                {
                    return BadRequest("This event is already in your itinerary!");
                }
            }
            else
            {
                return BadRequest("Neither Event or Private Event were selected");
            }


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
