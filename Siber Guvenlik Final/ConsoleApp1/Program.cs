using System;
using System.IO;
using System.Collections.Generic;
using System.Diagnostics;
using System.Xml;
using System.Threading.Tasks;
using System.Runtime.CompilerServices;

namespace ConsoleApp1
{
    class Program
    {
        class NMAP : IDisposable
        {
            private ProcessStartInfo ps = new ProcessStartInfo();
            private Process p = new Process();
            private string output;
            private string script;
            public NMAP(string script)
            {
                this.script = script;
                ps.Arguments = "-p 80 --script " + this.script + " testphp.vulnweb.com -oX -";
                ps.RedirectStandardOutput = true;
                ps.FileName = "nmap";
                p.StartInfo = ps;
            }

            public void Dispose()
            {
                p.Dispose();
            }

            private void TakeOutput()
            {
                if (string.IsNullOrEmpty(output))
                {
                    p.Start();
                    output = p.StandardOutput.ReadToEnd();
                    p.WaitForExit();
                    p.Close();
                }
            }
            public string StdOut
            {
                get
                {
                    TakeOutput();
                    return output;
                }
            }
        }
        static void Main(String[] args)
        {
            List<NMAP> nmaps = new List<NMAP>();
            nmaps.Add(new NMAP("http-sql-injection"));
            nmaps.Add(new NMAP("ssl-ccs-injection"));
            nmaps.Add(new NMAP("http-csrf"));
            //nmaps.ForEach(x => Console.WriteLine(x.StdOut));
            StreamWriter sw = new StreamWriter("result.xml");
            nmaps.ForEach(x => sw.WriteLine(x.StdOut));
            sw.Flush();
            sw.Close();
        }
    }
}
