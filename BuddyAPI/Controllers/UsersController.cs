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
            return await _context.User.ToListAsync();
        }


        // GET: api/Users/5
        [HttpGet("getUserById")]
        public async Task<ActionResult<User>> GetUser(int id)
        {
            var user = await _context.User.FindAsync(id);

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

        // PUT: api/Users/5
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
                        //Saves User object as a Session Variable
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

        //GET: Session User object
        [HttpGet ("GetSession")]
        public User GetSession()
        {
            HttpContext.Session.SessionExists("UserLoggedIn");
           
            User currentUser = HttpContext.Session.GetObjectFromJson<User>("UserLoggedIn");
            return currentUser;
        }

        //Removes current seesion of User logged in
        [HttpGet ("Logout")]
        public string Logout()
        {
            HttpContext.Session.Remove("UserLoggedIn");

            //Removes All Sessions currently Stored.
            HttpContext.Session.Clear();
            HttpContext.SignOutAsync();

            return "User has been successfully Logged out";

        }

        //Returns boolean if User exists in the Database
        private bool UserExists(int id)
        {
            return _context.User.Any(e => e.User_Id == id);
        }

        //Returns User object if email exists within the database
        private User EmailExists(string email)
        {
            return _context.User.FirstOrDefault(e => e.Email == email);
        }


        //Hashes password 
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
