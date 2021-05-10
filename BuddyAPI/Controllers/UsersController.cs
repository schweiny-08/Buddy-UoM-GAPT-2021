using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BuddyAPI.Models;
using BuddyAPI.ViewModels;
using BuddyAPI.Data;
using Microsoft.AspNetCore.Mvc.Rendering;
using System.Text;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.AspNetCore.Authentication;

namespace BuddyAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly BuddyAPIContext _context;

        public UsersController(BuddyAPIContext context)
        {
            _context = context;
        }



        // GET: api/Users
        [HttpGet("getAllUsers")]
        public async Task<ActionResult<IEnumerable<User>>> GetUsers()
        {
            //var BuddyApiContextRoles = _context.User.Include(u => u.Roles);
            //return await BuddyApiContextRoles.ToListAsync();
            return await _context.User.ToListAsync();
        }


        // GET: api/Users/5
        [HttpGet("getUserById")]
        public async Task<ActionResult<User>> GetUser(int id)
        {

            //var user = await _context.User
            //   .Include(i => i.Roles)
            //   .FirstOrDefaultAsync(i => i.User_Id == id);
            var user = await _context.User.FindAsync(id);

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

        // PUT: api/Users/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("editUserById")]
        public async Task<IActionResult> PutUser(int id, User user)
        {
            if (id != user.User_Id)
            {
                return BadRequest();
            }
            user.Password = HashPassword(user.Email, user.Password);
            _context.Entry(user).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
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

        // POST: api/Users
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("addUser")]
        public async Task<ActionResult<User>> PostUser(User user)
        {

            _context.User.Add(user);
            await _context.SaveChangesAsync();

            //var users = await _context.User
            //    .Include(i => i.Roles)
            //    .FirstOrDefaultAsync(i => i.Role_Id == user.Role_Id);

            //return JsonResult(users);

            return CreatedAtAction(nameof(GetUser), new { id = user.User_Id }, user);
            //return CreatedAtAction(nameof(GetUser), new { id = users.User_Id }, users);

        }

        // DELETE: api/Users/5
        [HttpDelete("deleteUserById")]
        public async Task<IActionResult> DeleteUser(int id)
        {
            var user = await _context.User.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }

            _context.User.Remove(user);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        [HttpPost("Register")]
        public async Task<IActionResult> Register([Bind ("User_Id,Role_Id,Username,Email,Telephone,Password")] User user)
        {
            var emailExists = await _context.User.FirstOrDefaultAsync(m => m.Email == user.Email);

            if(emailExists == null)
            {
                user.Password = HashPassword(user.Email, user.Password);

                //user.Roles.Role_Id = user.Role_Id;

                //RolesController rc = new RolesController(_context);
                //var role = rc.GetRoles(user.Role_Id);

                //user.Roles.RoleType = role.Result.Value.RoleType;
                //user.Roles.Users = (ICollection<User>)user;

                _context.Add(user);
                await _context.SaveChangesAsync();

                HttpContext.Session.SetObjectAsJson("UserLoggedIn", user);
                return CreatedAtAction(nameof(GetUser), new { id = user.User_Id }, user);
            } else
            {
                throw new ArgumentException($"Email {user.Email} already exists in the database, try another");
            }
          
        }
        

        [HttpPost("Login")]
        public async Task<ActionResult<User>> Login(string email, string password)
        {
            var dbUser = EmailExists(email);

            if (dbUser != null)
            {
                if (password != null)
                {
                    //Hash Password Method here
                    password = HashPassword(email, password);

                    if (dbUser.Password == password)
                    {
                        HttpContext.Session.SetObjectAsJson("UserLoggedIn", dbUser);
                        return CreatedAtAction(nameof(GetUser), new { id = dbUser.User_Id }, dbUser);
                    }
                    else
                    {
                        throw new ArgumentException($"Password entered doesn't match in the database");
                    }
                }
                else
                {
                    throw new ArgumentException($"Oopsie you forgot to input a password");

                }

            }
            else
            {
                throw new ArgumentException($"The email doesn't exist in the database for {email}");
            }
        }

        [HttpGet ("GetSession")]
        public User GetSession()
        {
            HttpContext.Session.SessionExists("UserLoggedIn");
           
            User currentUser = HttpContext.Session.GetObjectFromJson<User>("UserLoggedIn");
            return currentUser;
        }

        [HttpGet ("Logout")]
        public string Logout()
        {
            HttpContext.Session.Remove("UserLoggedIn");

            //Removes All Sessions currently Stored.
            HttpContext.Session.Clear();
            HttpContext.SignOutAsync();

            return "User has been successfully Logged out";
            //return RedirectToAction(nameof(GetSession));

        }


        private bool UserExists(int id)
        {
            return _context.User.Any(e => e.User_Id == id);
        }

        private User EmailExists(string email)
        {
            return _context.User.FirstOrDefault(e => e.Email == email);
        }

        private string HashPassword(string Email, string Password)
        {
            byte[] salt = Encoding.ASCII.GetBytes(Email);

            // derive a 256-bit subkey (use HMACSHA1 with 10,000 iterations)
            string hashed = Convert.ToBase64String(KeyDerivation.Pbkdf2(
                password: Password,
                salt: salt,
                prf: KeyDerivationPrf.HMACSHA1,
                iterationCount: 10000,
                numBytesRequested: 256 / 8));

            return hashed;
        }
        }
    }
