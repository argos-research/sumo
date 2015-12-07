function speed = getSpeed(personID)
%getSpeed Get the person speed.
%   speed = getSpeed(PERSONID) Returns the speed in m/s of the named person 
%   within the last step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
speed = traci.person.getUniversal(constants.VAR_SPEED, personID);