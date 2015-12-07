function position = getPosition(personID)
%getPosition Returns the position of the named person within the last step.
%   position = getPosition(PERSONID) Returns the x,y position of the named 
%   person within the last step [m,m].

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
position = traci.person.getUniversal(constants.VAR_POSITION, personID);