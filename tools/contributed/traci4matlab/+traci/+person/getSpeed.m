function speed = getSpeed(personID)
%getSpeed Get the person speed.
%   speed = getSpeed(PERSONID) Returns the speed in m/s of the named person 
%   within the last step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getSpeed.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
speed = traci.person.getUniversal(constants.VAR_SPEED, personID);