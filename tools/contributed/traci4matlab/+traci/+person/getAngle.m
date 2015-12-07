function angle = getAngle(personID)
%getAngle Get the angle of the person.
%   angle = getAngle(PERSONID) Returns the angle in degrees of the named 
%   person within the last step. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
angle = traci.person.getUniversal(constants.VAR_ANGLE, personID);