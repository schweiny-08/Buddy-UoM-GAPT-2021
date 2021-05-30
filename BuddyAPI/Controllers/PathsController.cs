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
    public class PathsController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public PathsController(BuddyAPIContext context)
        {
            _context = context;
        }

        // GET: api/Paths
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Paths>>> GetPaths()
        {
            return await _context.Paths.ToListAsync();
        }

        // GET: api/Paths/5
        [HttpGet("{id}")]
        //retrieves a list of IDs connected to the given ID
        public async Task<ActionResult<IEnumerable<int>>> GetPaths(int id)
        {

            List<int> paths = new List<int>();
            foreach (var p in _context.Paths)
            {
                if (p.pinpoint_Id == id)
                {
                   paths.Add(p.pinpoint_Id_2);
                }
                else if (p.pinpoint_Id_2 == id)
                {
                    paths.Add(p.pinpoint_Id);
                }
            }

            return paths;
        }

        // PUT: api/Paths/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutPaths(int id, Paths paths)
        {
            if (id != paths.pinpoint_Id)
            {
                return BadRequest();
            }

            _context.Entry(paths).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PathsExists(paths.pinpoint_Id_2, paths.pinpoint_Id))
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

        // POST: api/Paths
        [HttpPost]
        public async Task<ActionResult<Paths>> PostPaths(Paths paths)
        {
            _context.Paths.Add(paths);


            if (PathsExists(paths.pinpoint_Id_2, paths.pinpoint_Id))
            {
                return Conflict();
            }
            else
            {
                await _context.SaveChangesAsync();

            }


            return CreatedAtAction("GetPaths", new { id = paths.pinpoint_Id }, paths);
        }

        // DELETE: api/Paths/5
        [HttpDelete("{id}")]
        //deletes the given path of 2 pinpoints
        public async Task<IActionResult> DeletePaths(int id, int id2)
        {
            var paths = await _context.Paths.FirstOrDefaultAsync(a => a.pinpoint_Id == id && a.pinpoint_Id_2 == id2);
            var paths2 = await _context.Paths.FirstOrDefaultAsync(a => a.pinpoint_Id == id2 && a.pinpoint_Id_2 == id);

            if (paths == null && paths2 == null)
            {
                return NotFound();
            }
            else if (paths != null && paths2 == null)
            {
                _context.Paths.Remove(paths);
            }
            else
            {
                _context.Paths.Remove(paths2);
            }

            await _context.SaveChangesAsync();

            return NoContent();
        }

        //checks if the given path exists
        private bool PathsExists(int id2, int id)
        {
            bool check1 = _context.Paths.Any(e => e.pinpoint_Id == id2 && e.pinpoint_Id_2 == id);
            bool check2 = _context.Paths.Any(e => e.pinpoint_Id == id && e.pinpoint_Id_2 == id2);

            if (check1 || check2)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
