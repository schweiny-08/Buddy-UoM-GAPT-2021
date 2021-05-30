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
    public class PinpointTypesController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public PinpointTypesController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/PinpointTypes
        [HttpGet("getAllPinpointTypes")]
        public async Task<ActionResult<IEnumerable<PinpointTypes>>> GetPinpointTypes()
        {
            return await _context.PinpointTypes.ToListAsync();
        }

        // GET: api/PinpointTypes/5
        [HttpGet("getPinpointTypeById")]
        public async Task<ActionResult<PinpointTypes>> GetPinpointTypes(int id)
        {
            var pinpointTypes = await _context.PinpointTypes.FindAsync(id);

            if (pinpointTypes == null)
            {
                return NotFound();
            }

            return pinpointTypes;
        }

        [HttpGet("getPinpointTypeByName")]
        public async Task<ActionResult<PinpointTypes>> GetPinpointTypesByName(string name)
        {
            var pinpointTypes = _context.PinpointTypes.FirstOrDefault(p => p.pinpointTypeName == name);

            if (pinpointTypes == null)
            {
                return NotFound();
            }

            return pinpointTypes;
        }

        // PUT: api/PinpointTypes/5
        [HttpPut("editPinpointTypeById")]
        public async Task<IActionResult> PutPinpointTypes(int id, PinpointTypes pinpointTypes)
        {
            if (id != pinpointTypes.pinpointType_Id)
            {
                return BadRequest();
            }

            _context.Entry(pinpointTypes).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PinpointTypesExists(id))
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

        // POST: api/PinpointTypes
        [HttpPost("addPinpointType")]
        public async Task<ActionResult<PinpointTypes>> PostPinpointTypes(PinpointTypes pinpointTypes)
        {
            var existingPPType = _context.PinpointTypes.FirstOrDefault(p => p.pinpointTypeName == pinpointTypes.pinpointTypeName);

            //Validation check to ensure that PinpointType Name doesn't already exisit within the database
            if (existingPPType!= null && existingPPType.pinpointTypeName.Equals(pinpointTypes.pinpointTypeName, StringComparison.OrdinalIgnoreCase))
                return BadRequest("Pinpoint type already exists!");

            _context.PinpointTypes.Add(pinpointTypes);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPinpointTypes", new { id = pinpointTypes.pinpointType_Id }, pinpointTypes);
        }

        // DELETE: api/PinpointTypes/5
        [HttpDelete("deletePinpointTypeById")]
        public async Task<IActionResult> DeletePinpointTypes(int id)
        {
            var pinpointTypes = await _context.PinpointTypes.FindAsync(id);
            if (pinpointTypes == null)
            {
                return NotFound();
            }

            _context.PinpointTypes.Remove(pinpointTypes);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        //Returns boolean if PinpointType already exists within the database
        private bool PinpointTypesExists(int id)
        {
            return _context.PinpointTypes.Any(e => e.pinpointType_Id == id);
        }
    }
}
