classdef Phase
%Phase Construct a Phase object for TraCI traffic lights.
%   myPhase = Phase(DURATION,DURATION1,DURATION2,PHASEDEF) Constructs a 
%   Phase object with the properties DURATION, DURATION1, which is the min 
%   duration in miliseconds; DURATION2, which is the max duration in 
%   miliseconds and PHASEDEF, which is a string of light definitions from 
%   rRgGyYoO for red, green, yellow, off, where lower case letters mean 
%   that the stream has to decelerate.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: Phase.m 31 2016-09-28 15:16:56Z afacostag $

    properties
        duration
        duration1
        duration2
        phaseDef
    end
    methods
        function this = Phase(duration, duration1, duration2, phaseDef)          
            this.duration = duration;
            this.duration1 = duration1;
            this.duration2 = duration2;
            this.phaseDef = phaseDef;
        end
        
        function display(this)
            disp('Phase:');
            disp(['duration: ' num2str(this.duration)]);
            disp(['duration1: ' num2str(this.duration1)]);
            disp(['duration2: ' num2str(this.duration1)]);
            disp(['phaseDef: ' num2str(this.phaseDef)]);
        end
        
    end
end